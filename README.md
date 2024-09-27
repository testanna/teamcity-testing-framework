
# TeamCity Testing Framework

**Workshop**: [Test Automation from Scratch](https://pshe-academy.com/workshop_test_automation_from_scratch/java)

This repository contains tasks from the workshop "Test Automation from Scratch" and includes examples of using Java for UI and API testing with:

- **REST Assured**
- **Selenide**
- **TestNG**
- **Allure Report**

You can open the project using **IntelliJ IDEA Community Edition**.

## Running Tests Locally

For running tests locally for the first time, you can use the `setup_infra.sh` script. Before running the project, ensure you have the following installed on your machine:

- **Docker**: Required to run the TeamCity server, agents, and Selenoid for browser testing.  
  On Windows, it's recommended to use **Docker Desktop** with **WSL2** for better compatibility.
  
- **curl**: Used by the script to check the status of services.  
  On Linux and macOS, it's usually pre-installed. On Windows, it may need to be installed or used within WSL2.

### Available Ports:

- `8111` for TeamCity Server
- `4444` for Selenoid
- `8080` for Selenoid UI

Ensure these ports are not in use by other services.

### To execute tests, use Maven:

```bash
mvn clean test
```

After the tests are executed, generate and view the Allure report:

```bash
mvn allure:serve
```

## Remote Test Execution with GitHub Actions

This project is configured to run tests remotely using **GitHub Actions**. The workflow is defined in the file [.github/workflows/test.yml](https://github.com/testanna/teamcity-testing-framework/blob/master/.github/workflows/test.yml). 

To monitor the status of workflows, navigate to the **Actions** tab in the GitHub repository. After the tests have been executed, you can find the test results [here](https://testanna.github.io/teamcity-testing-framework/).
