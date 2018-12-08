package com.github.chenjianjx.srb4jfullsample.impl.itcase;

import com.github.chenjianjx.srb4jfullsample.datamigration.MigrationRunner;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.ListContainersParam;
import com.spotify.docker.client.DockerClient.LogsParam;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ExecCreation;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Log4jConfigurer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by chenjianjx@gmail.com on 10/11/18.
 */
public class BaseITCase {

    private static final String MYSQL_CONTAINER_NAME = "srb4jfullsample_db_container";
    private static Logger logger;
    private static final int MYSQL_PORT = 10603;
    private static final String MYSQL_SCHEMA = "srb4jfullsample_integration_test";
    public static final String MYSQL_JDBC_URL =
            String.format("jdbc:mysql://localhost:%s/%s?characterEncoding=UTF-8", MYSQL_PORT, MYSQL_SCHEMA);
    public static final String MYSQL_ROOT_PASSWORD = "root";
    private static String mysqlContainerId;
    private static DockerClient docker;
    private static MigrationRunner runner = new MigrationRunner();

    @BeforeClass
    public static void init() throws Exception {
        Log4jConfigurer.initLogging("classpath:log4j-test.xml");
        logger = LoggerFactory.getLogger(BaseITCase.class);

        docker = new DefaultDockerClient("unix:///var/run/docker.sock");

        logger.info("mysqlContainerId is " + mysqlContainerId);
        if (mysqlContainerId == null) {
            createMySqlContainer(docker);
        }

    }

    private static void createMySqlContainer(DockerClient docker) throws DockerException, InterruptedException {


        String mysqlImageName = "mysql:5.7";

        if (docker.listImages().stream().noneMatch(image -> image.repoTags().get(0).equals(mysqlImageName))) {
            docker.pull(mysqlImageName);
        }

        logger.info("Mysql docker image is ready");

        Optional<Container> container = docker.listContainers(ListContainersParam.allContainers()).stream()
                .filter(c -> c.names().get(0).contains(MYSQL_CONTAINER_NAME)).findFirst();
        if (container.isPresent()) {
            if ("running".equals(container.get().state())) {
                docker.killContainer(container.get().id());
            }
            docker.removeContainer(container.get().id());
        }

        final Map<String, List<PortBinding>> portBindings = new HashMap<>();
        portBindings.put("3306", Arrays.asList(PortBinding.of("127.0.0.1", MYSQL_PORT)));


        final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();


        final ContainerConfig containerConfig = ContainerConfig.builder()
                .hostConfig(hostConfig)
                .image(mysqlImageName)
                .exposedPorts("3306")
                .env("MYSQL_ROOT_PASSWORD=" + MYSQL_ROOT_PASSWORD)
                .build();

        final ContainerCreation creation = docker.createContainer(containerConfig, MYSQL_CONTAINER_NAME);
        mysqlContainerId = creation.id();
        docker.startContainer(mysqlContainerId);
        logger.info("Mysql docker container is starting up...");
        logger.info("Sleep some time for the container to be ready");
        for (int i = 15; i > 0; i--) {
            Thread.sleep(1000);
            logger.info(i + " ");
        }

        try (LogStream stream = docker.logs(mysqlContainerId, LogsParam.stdout(), LogsParam.stderr())) {
            logger.info(stream.readFully());
        }


        final String[] createDbCommand = {"/usr/bin/mysql", "-u", "root",
                "-p" + MYSQL_ROOT_PASSWORD,
                "-e", String.format("create database %s default character set utf8;", MYSQL_SCHEMA)};
        final ExecCreation createDbExec = docker.execCreate(
                mysqlContainerId, createDbCommand, DockerClient.ExecCreateParam.attachStdout(),
                DockerClient.ExecCreateParam.attachStderr());
        final String createDbExecOutput = docker.execStart(createDbExec.id()).readFully();
        logger.info(createDbExecOutput);

        Thread.sleep(1 * 1000);

        ContainerInfo containerInfo = docker.inspectContainer(mysqlContainerId);
        if (!containerInfo.state().running()) {
            throw new IllegalStateException("Mysql docker container is not running. Please check the log above.");
        }

        //now create the tables
        runner.run(MYSQL_JDBC_URL, "root", MYSQL_ROOT_PASSWORD);

    }


    @AfterClass
    public static void tearDown() throws DockerException, InterruptedException {
        for (int i = 0; i < 5; i++) {
            System.out.println("===================================================");
        }
        for (int i = 0; i < 1; i++) {
            System.out.println("====Separator of integration tests=================");
        }
        for (int i = 0; i < 5; i++) {
            System.out.println("===================================================");
        }

    }

}
