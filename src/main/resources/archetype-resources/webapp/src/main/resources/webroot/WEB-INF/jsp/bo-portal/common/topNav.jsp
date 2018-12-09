<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <nav class="navbar navbar-expand navbar-dark bg-dark static-top">

      <a class="navbar-brand mr-1" href="#">Srb4jfullsample</a>

      <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#">
        <i class="fas fa-bars"></i>
      </button>

      <div class="d-none d-md-inline-block ml-auto mr-0 mr-md-3 my-2 my-md-0"></div>

      <!-- Navbar -->
      <ul class="navbar-nav ml-auto ml-md-0">
        <li class="nav-item dropdown no-arrow">
          <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-user-circle fa-fw"></i>
          </a>
          <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
            <div class="dropdown-item" href="#">Hi, <c:out value="${sessionStaffUser.username}"/></div>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="/bo/portal/staffusers/admin/change-password">Change password</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="/bo/portal/logout">Logout</a>
          </div>
        </li>
      </ul>

    </nav>