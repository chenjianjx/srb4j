<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

      <!-- Sidebar -->
      <ul class="sidebar navbar-nav">

        <li class= '${it.relativeUrl == "/" ? "nav-item active": "nav-item"}'>
          <a class="nav-link" href="/bo/portal/">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Dashboard</span>
          </a>
        </li>

        <li class= '${it.relativeUrl == "/frontusers" ? "nav-item active": "nav-item"}'>
          <a class="nav-link" href="/bo/portal/frontusers" >
            <i class="fas fa-fw fa-users"></i>
            <span>Front Users</span>
          </a>
        </li>

      </ul>