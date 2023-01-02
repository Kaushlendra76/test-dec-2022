package net.guides.springboot;


import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.MySQLContainer;

class AbstractContainerBaseTest {

    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");

        MY_SQL_CONTAINER.start();
    }
}
