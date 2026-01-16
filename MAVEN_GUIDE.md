# Complete Maven Guide

## Table of Contents
1. [What is Maven?](#what-is-maven)
2. [What are its Uses?](#what-are-its-uses)
3. [How Maven Searches and Downloads Dependencies](#how-maven-searches-and-downloads-dependencies)
4. [Maven Folder Structure](#maven-folder-structure)
5. [JUnit Dependency Explanation](#junit-dependency-explanation)
6. [Common Maven Commands](#common-maven-commands)
7. [How to Execute the Example Script](#how-to-execute-the-example-script)

---

## What is Maven?

**Apache Maven** is a powerful project management and comprehension tool used primarily for Java projects. It provides a standardized way to build, manage, and understand software projects.

### Key Characteristics:

1. **Build Tool**: Maven automates the build process, including compilation, testing, packaging, and deployment.

2. **Project Management Tool**: It manages project dependencies, documentation, reporting, and distribution.

3. **Convention over Configuration**: Maven follows a standard directory structure, reducing the need for configuration. If you follow Maven's conventions, you need minimal setup.

4. **Dependency Management**: Maven automatically downloads required libraries (dependencies) from repositories and manages their versions.

5. **Project Object Model (POM)**: Maven uses an XML file called `pom.xml` to describe the project, its dependencies, build configuration, and more.

6. **Lifecycle-Based Build**: Maven has predefined build lifecycles (clean, validate, compile, test, package, verify, install, deploy) that execute in a specific order.

---

## What are its Uses?

Maven is used for various purposes in software development:

### 1. **Dependency Management**
   - Automatically downloads and manages project dependencies
   - Resolves transitive dependencies (dependencies of dependencies)
   - Manages version conflicts
   - Example: Instead of manually downloading JAR files, you declare dependencies in `pom.xml`

### 2. **Build Automation**
   - Compiles source code
   - Runs unit tests
   - Packages applications (JAR, WAR, EAR files)
   - Generates documentation
   - Creates distribution packages

### 3. **Project Standardization**
   - Enforces a standard directory structure
   - Provides consistent build processes across projects
   - Makes projects easier to understand and maintain

### 4. **Multi-Module Projects**
   - Manages complex projects with multiple modules
   - Handles dependencies between modules
   - Builds all modules in the correct order

### 5. **Integration with IDEs**
   - Works seamlessly with IDEs like IntelliJ IDEA, Eclipse, NetBeans
   - IDE plugins can read `pom.xml` and configure projects automatically

### 6. **Continuous Integration**
   - Integrates with CI/CD tools like Jenkins, GitLab CI, GitHub Actions
   - Enables automated builds and deployments

### 7. **Documentation Generation**
   - Generates project documentation
   - Creates project reports
   - Generates API documentation

### 8. **Repository Management**
   - Publishes artifacts to repositories
   - Shares libraries across teams and projects
   - Manages releases and snapshots

---

## How Maven Searches and Downloads Dependencies

Understanding how Maven locates and downloads dependencies is crucial for effective dependency management. This section explains the complete process.

### Maven Repositories

Maven uses **repositories** to store and retrieve dependencies. There are three types of repositories:

#### 1. **Local Repository**
- **Location**: `~/.m2/repository/` (on Windows: `C:\Users\<username>\.m2\repository\`)
- **Purpose**: Stores all downloaded dependencies on your local machine
- **Structure**: Organized by groupId/artifactId/version
- **Example Path**: `~/.m2/repository/junit/junit/4.13.2/junit-4.13.2.jar`
- **Benefits**: 
  - Fast access (no network needed)
  - Works offline for previously downloaded dependencies
  - Shared across all Maven projects on your machine

#### 2. **Central Repository (Maven Central)**
- **URL**: `https://repo.maven.apache.org/maven2/`
- **Purpose**: The default public repository containing millions of open-source Java libraries
- **Access**: Automatically used by Maven (no configuration needed)
- **Content**: Most popular Java libraries (JUnit, Apache Commons, Spring, etc.)
- **Managed by**: Apache Software Foundation and Sonatype

#### 3. **Remote Repositories**
- **Purpose**: Custom repositories (company internal, third-party, etc.)
- **Configuration**: Defined in `pom.xml` or `settings.xml`
- **Examples**: 
  - Company Nexus/Artifactory servers
  - GitHub Packages
  - JitPack
  - Custom Maven repositories

### Repository Search Order

When Maven needs a dependency, it searches repositories in this order:

1. **Local Repository** (checked first)
   - If found locally, Maven uses it immediately (no download)
   - Fastest option

2. **Remote Repositories** (if configured)
   - Searches in the order defined in `pom.xml` or `settings.xml`
   - Typically includes company/internal repositories

3. **Maven Central Repository** (default fallback)
   - Searched if not found in local or remote repositories
   - Requires internet connection

### Dependency Resolution Process

When you declare a dependency in `pom.xml`:

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

Maven follows this process:

#### Step 1: Parse Dependency Information
- Reads `groupId`, `artifactId`, and `version` from `pom.xml`
- Constructs the dependency coordinates

#### Step 2: Check Local Repository
- Searches: `~/.m2/repository/junit/junit/4.13.2/`
- Looks for:
  - `junit-4.13.2.jar` (the JAR file)
  - `junit-4.13.2.pom` (the POM file with metadata)
- **If found**: Uses local copy (no download needed)
- **If not found**: Proceeds to Step 3

#### Step 3: Search Remote Repositories
- Checks configured remote repositories (if any)
- Downloads from the first repository that has the dependency

#### Step 4: Download from Maven Central
- If not found locally or in remote repos, downloads from Maven Central
- Downloads both:
  - **POM file**: Contains dependency metadata (dependencies of this dependency)
  - **JAR file**: The actual library

#### Step 5: Resolve Transitive Dependencies
- Reads the downloaded POM file
- Identifies dependencies of the dependency (transitive dependencies)
- Recursively resolves each transitive dependency
- Example: JUnit 4.13.2 depends on `hamcrest-core`, so Maven also downloads that

#### Step 6: Store in Local Repository
- Saves downloaded files to local repository
- Creates directory structure: `groupId/artifactId/version/`
- Stores for future use (caching)

#### Step 7: Handle Version Conflicts
- If multiple versions of the same dependency are found:
  - Uses "nearest wins" strategy (closest in dependency tree)
  - Or uses "first declaration wins" strategy
  - May show warnings about version conflicts

### Local Repository Structure

The local repository follows a standard directory structure:

```
~/.m2/repository/
└── junit/
    └── junit/
        └── 4.13.2/
            ├── junit-4.13.2.jar          # The actual library
            ├── junit-4.13.2.pom          # Dependency metadata
            ├── junit-4.13.2.jar.sha1     # Checksum for verification
            └── _remote.repositories      # Tracks download source
```

**Path Pattern**: `groupId/artifactId/version/`

### Download Process Details

#### What Gets Downloaded?

1. **POM File** (`*.pom`)
   - Contains project metadata
   - Lists transitive dependencies
   - Contains build configuration
   - Usually small (few KB)

2. **JAR File** (`*.jar`)
   - The actual compiled library
   - Contains `.class` files
   - Size varies (KB to MB)

3. **Checksum Files** (`*.sha1`, `*.md5`)
   - Used to verify file integrity
   - Ensures downloaded file wasn't corrupted

#### Download Behavior

- **First Time**: Downloads from remote repository (requires internet)
- **Subsequent Times**: Uses cached local copy (works offline)
- **Update Check**: Maven checks for updates based on:
  - SNAPSHOT versions: Always checks for updates
  - Release versions: Uses cached version unless forced to update

### Dependency Coordinates

Maven identifies dependencies using three coordinates:

1. **groupId**: Organization/company identifier
   - Example: `junit`, `org.apache.commons`, `com.google.guava`
   - Usually reverse domain name format

2. **artifactId**: Project/library name
   - Example: `junit`, `commons-lang3`, `guava`
   - Usually lowercase with hyphens

3. **version**: Version number
   - Example: `4.13.2`, `1.0-SNAPSHOT`, `3.12.0`
   - Formats: `major.minor.patch` or `major.minor.patch-SNAPSHOT`

**Complete Coordinate**: `groupId:artifactId:version`
- Example: `junit:junit:4.13.2`

### Transitive Dependencies

**Transitive dependencies** are dependencies of your dependencies.

**Example**:
- Your project depends on `junit:junit:4.13.2`
- JUnit 4.13.2 depends on `org.hamcrest:hamcrest-core:1.3`
- Maven automatically downloads `hamcrest-core` even though you didn't declare it

**Benefits**:
- No need to manually declare all dependencies
- Automatic dependency management
- Ensures compatible versions

**Viewing Transitive Dependencies**:
```bash
mvn dependency:tree
```

This shows the complete dependency tree including transitive dependencies.

### Dependency Scopes

Dependencies can have different **scopes** that affect when they're available:

- **compile** (default): Available in all classpaths
- **test**: Only available during test compilation and execution
- **provided**: Provided by JDK or container (not packaged)
- **runtime**: Needed at runtime but not for compilation
- **system**: Similar to provided but requires explicit path
- **import**: Used for dependency management only (BOM files)

### Caching and Offline Mode

#### Local Cache Benefits

- **Speed**: Local access is much faster than downloading
- **Offline Work**: Previously downloaded dependencies work without internet
- **Bandwidth**: Reduces network usage
- **Consistency**: Same version used across projects

#### Forcing Updates

To force Maven to re-download dependencies:

```bash
# Update SNAPSHOT dependencies
mvn clean install -U

# Force update all dependencies
mvn dependency:resolve -U
```

#### Offline Mode

To work completely offline (use only local repository):

```bash
mvn clean install -o
# or
mvn clean install --offline
```

**Note**: This only works if all required dependencies are already in your local repository.

### Common Issues and Solutions

#### 1. **Dependency Not Found**
- **Error**: `Could not find artifact`
- **Causes**: 
  - Wrong version number
  - Dependency doesn't exist in Maven Central
  - Network connectivity issues
- **Solutions**:
  - Check version exists: `mvn versions:display-dependency-updates`
  - Verify groupId/artifactId spelling
  - Check internet connection
  - Add custom repository if needed

#### 2. **Slow Downloads**
- **Causes**: Network issues, large dependencies
- **Solutions**:
  - Use local repository mirror
  - Configure faster repository
  - Pre-download dependencies

#### 3. **Version Conflicts**
- **Error**: Multiple versions of same dependency
- **Solutions**:
  - Use `mvn dependency:tree` to identify conflicts
  - Explicitly declare the version you want
  - Use `<dependencyManagement>` section

### Viewing Repository Information

#### Check Local Repository Location
```bash
mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout
```

#### List All Repositories Used
```bash
mvn help:effective-settings
```

#### View Dependency Sources
```bash
mvn dependency:sources
mvn dependency:resolve -Dclassifier=sources
```

### Summary

Maven's dependency management works through a hierarchical repository system:

1. **Local Repository** → Fast, cached, offline-capable
2. **Remote Repositories** → Custom/company repositories
3. **Maven Central** → Default public repository

The process is automatic: declare a dependency in `pom.xml`, and Maven handles the rest - searching, downloading, caching, and resolving transitive dependencies.

---

## Maven Folder Structure

Maven follows a **Convention over Configuration** approach with a standard directory layout:

```
project-root/
│
├── pom.xml                          # Project Object Model - main configuration file
│
├── src/                             # Source code directory
│   ├── main/                        # Main application code
│   │   ├── java/                    # Java source files
│   │   │   └── [package structure]/ # Package directories matching Java package names
│   │   │       └── *.java           # Java source files
│   │   ├── resources/               # Resource files (config files, properties, etc.)
│   │   │   └── *.properties         # Configuration files
│   │   └── webapp/                  # Web application resources (for WAR projects)
│   │       └── WEB-INF/
│   │
│   └── test/                        # Test code
│       ├── java/                    # Test Java source files
│       │   └── [package structure]/ # Test package directories
│       │       └── *Test.java       # Test files
│       └── resources/               # Test resource files
│
└── target/                          # Build output directory (created by Maven)
    ├── classes/                     # Compiled classes
    ├── test-classes/                # Compiled test classes
    ├── [project-name].jar           # Generated JAR file
    └── surefire-reports/            # Test reports
```

### Key Directories Explained:

- **`src/main/java/`**: Contains your main application source code
- **`src/main/resources/`**: Contains configuration files, properties files, XML files, etc.
- **`src/test/java/`**: Contains unit test source code
- **`src/test/resources/`**: Contains test-specific resources
- **`target/`**: Generated by Maven during build; contains compiled classes, JAR files, test reports, etc. (This directory is typically ignored in version control)

### Our Project Structure:

```
java_maven/
├── pom.xml
└── src/
    ├── main/
    │   └── java/
    │       └── navaneeth/
    │           └── App.java          # Default Hello World example
    └── test/
        └── java/
            └── navaneeth/
                ├── AppTest.java      # Default test file
                └── Calculator.java   # Calculator test script
```

**Note**: `Calculator.java` is a test script and is located in the `src/test/java/` folder. This allows it to be used for writing and running test scripts.

---

## JUnit Dependency Explanation

### Why is JUnit Dependency Added?

The JUnit dependency is automatically included in the `pom.xml` file when you create a Maven project using the `maven-archetype-quickstart` archetype. Here's why:

1. **Generated by Maven Archetype**: 
   - The `maven-archetype-quickstart` archetype automatically creates a sample test file (`AppTest.java`) in the `src/test/java/` directory
   - This test file uses JUnit framework classes, so JUnit must be available as a dependency

2. **AppTest.java Uses JUnit**:
   - The generated `AppTest.java` file extends `junit.framework.TestCase` (JUnit 3.x style)
   - It imports JUnit classes: `junit.framework.Test`, `junit.framework.TestCase`, `junit.framework.TestSuite`
   - Contains test methods that use JUnit's assertion methods (like `assertTrue()`)

3. **Test Scope**:
   - The dependency has `<scope>test</scope>`, which means:
     - JUnit is only available during test compilation and execution
     - It's **NOT** included in the final JAR file
     - It's **NOT** available to your main application code
     - This keeps your production code lightweight

4. **Maven Test Execution**:
   - When you run `mvn test`, Maven:
     - Compiles your test classes (which require JUnit to compile)
     - Uses the **Maven Surefire Plugin** (a Maven plugin, not a JUnit plugin) to discover and execute JUnit tests
     - Generates test reports showing which tests passed or failed
   - **Important**: Surefire is a **Maven plugin** (`maven-surefire-plugin`) that works with multiple testing frameworks (JUnit, TestNG, etc.). It's not a JUnit-specific plugin.

### JUnit Version in This Project

This project uses **JUnit 3.8.1**, which is the older version of JUnit. Modern projects typically use:
- **JUnit 4.x**: Uses annotations (`@Test`, `@Before`, `@After`)
- **JUnit 5.x**: The latest version with improved features

The archetype uses JUnit 3.x for backward compatibility, but you can upgrade to JUnit 4 or 5 if needed.

### Running JUnit Tests

To execute all JUnit unit tests in your project:

```bash
mvn test
```

This command will:
1. Compile main source code (`src/main/java/`)
2. Compile test source code (`src/test/java/`)
3. Execute all JUnit test classes using the **Maven Surefire Plugin**
4. Display test results in the console
5. Generate detailed reports in `target/surefire-reports/`

**Note**: The Maven Surefire Plugin is automatically included with Maven and handles test execution. It's a Maven plugin that supports multiple testing frameworks (JUnit, TestNG, etc.), not a JUnit-specific plugin.

**Example Output:**
```
[INFO] --- surefire:3.x.x:test (default-test) @ java_maven ---
[INFO] Running navaneeth.AppTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.xxx s
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
```

### Viewing Test Reports

After running tests, you can view detailed reports:
- **HTML Report**: `target/surefire-reports/index.html`
- **XML Reports**: `target/surefire-reports/*.xml` (one per test class)

### When You Don't Need JUnit

If you're not writing unit tests and only using the test folder for test scripts (like `Calculator.java`), you can:
- **Keep it**: No harm in having it, and you might need it later
- **Remove it**: If you remove the dependency, make sure `AppTest.java` doesn't cause compilation errors, or remove/update that file as well

---

## Common Maven Commands

Maven commands are called **goals** and are executed using the `mvn` command. Here are the most commonly used Maven commands with detailed explanations:

### 1. **`mvn clean`**
   - **Purpose**: Removes the `target/` directory and all compiled files
   - **When to use**: When you want a fresh build or to clean up generated files
   - **Example**: `mvn clean`
   - **What it does**: Deletes the entire `target/` folder

### 2. **`mvn compile`**
   - **Purpose**: Compiles the main source code
   - **When to use**: To compile your Java source files without running tests
   - **Example**: `mvn compile`
   - **What it does**: 
     - Downloads dependencies (if needed)
     - Compiles `src/main/java/` to `target/classes/`
     - Does NOT compile or run tests

### 3. **`mvn test`**
   - **Purpose**: Compiles both main and test code, then runs unit tests using JUnit
   - **When to use**: To verify your code works correctly by executing all unit tests
   - **Example**: `mvn test`
   - **What it does**:
     - Runs `compile` first (compiles main code)
     - Compiles test code from `src/test/java/`
     - Executes all JUnit test classes (classes that extend `TestCase` or use JUnit annotations)
     - Generates test reports in `target/surefire-reports/`
     - Shows test results in the console (tests run, failures, errors, skipped)
   - **Maven Surefire Plugin**: Maven uses the **maven-surefire-plugin** (a Maven plugin, not a JUnit plugin) to discover and execute JUnit tests automatically
   - **Test Reports**: After execution, check `target/surefire-reports/` for detailed HTML and XML test reports
   - **Note**: Surefire is a Maven plugin that works with multiple testing frameworks (JUnit, TestNG, etc.), not a JUnit-specific plugin

### 4. **`mvn package`**
   - **Purpose**: Compiles, tests, and packages the project into a JAR, WAR, or EAR file
   - **When to use**: To create a distributable artifact
   - **Example**: `mvn package`
   - **What it does**:
     - Runs `test` first (compile + test)
     - Packages compiled classes into a JAR/WAR/EAR file
     - Places the artifact in `target/` directory
   - **Note**: Skips tests if they fail (unless configured otherwise)

### 5. **`mvn install`**
   - **Purpose**: Compiles, tests, packages, and installs the artifact into the local Maven repository
   - **When to use**: To make your project available to other local projects
   - **Example**: `mvn install`
   - **What it does**:
     - Runs `package` first
     - Installs the JAR file to your local Maven repository (usually `~/.m2/repository/`)
     - Other projects on your machine can now use this as a dependency

### 6. **`mvn clean install`**
   - **Purpose**: Combines clean and install - removes old build files and creates a fresh build
   - **When to use**: For a complete fresh build
   - **Example**: `mvn clean install`
   - **What it does**: 
     - First runs `clean` (removes target/)
     - Then runs `install` (compile, test, package, install)

### 7. **`mvn clean package`**
   - **Purpose**: Cleans and packages the project
   - **When to use**: To create a fresh JAR file without installing
   - **Example**: `mvn clean package`
   - **What it does**: Clean + compile + test + package

### 8. **`mvn validate`**
   - **Purpose**: Validates that the project is correct and all necessary information is available
   - **When to use**: To check project configuration
   - **Example**: `mvn validate`
   - **What it does**: Checks POM file for errors

### 9. **`mvn verify`**
   - **Purpose**: Runs any checks to verify the package is valid and meets quality criteria
   - **When to use**: Before deploying
   - **Example**: `mvn verify`
   - **What it does**: Runs integration tests and quality checks

### 10. **`mvn deploy`**
   - **Purpose**: Copies the final package to a remote repository
   - **When to use**: To publish your artifact to a shared repository
   - **Example**: `mvn deploy`
   - **What it does**: 
     - Runs `install` first
     - Uploads artifact to remote repository (requires repository configuration)

### 11. **`mvn dependency:tree`**
   - **Purpose**: Displays the dependency tree showing all dependencies and their transitive dependencies
   - **When to use**: To understand what libraries your project uses
   - **Example**: `mvn dependency:tree`
   - **What it does**: Shows a tree structure of all dependencies

### 12. **`mvn dependency:resolve`**
   - **Purpose**: Downloads all dependencies without compiling
   - **When to use**: To download dependencies for offline work
   - **Example**: `mvn dependency:resolve`
   - **What it does**: Downloads all JAR files to local repository

### 13. **`mvn versions:display-dependency-updates`**
   - **Purpose**: Displays available updates for all dependencies in your project
   - **When to use**: To find the latest versions of your dependencies
   - **Example**: `mvn versions:display-dependency-updates`
   - **What it does**: 
     - Checks Maven Central repository for newer versions
     - Shows current version → latest available version for each dependency
     - Example output: `junit:junit 4.0.0 -> 4.13.2`
   - **Note**: Requires the `versions-maven-plugin` (usually auto-downloaded)
   - **Common Use Case**: If you get an error like "POM for junit:junit:jar:4.0.0 is missing", this command will show you the correct available version (e.g., 4.13.2)

### 14. **`mvn versions:use-latest-versions`**
   - **Purpose**: Automatically updates all dependencies to their latest versions in `pom.xml`
   - **When to use**: To update all dependencies at once (use with caution)
   - **Example**: `mvn versions:use-latest-versions`
   - **What it does**: 
     - Modifies your `pom.xml` to use the latest available versions
     - Creates a backup of your original `pom.xml` as `pom.xml.versionsBackup`
   - **Warning**: Review changes before committing, as this updates ALL dependencies

### 15. **`mvn help:effective-pom`**
   - **Purpose**: Shows the effective POM (your POM + parent POMs + default settings)
   - **When to use**: To see the final configuration Maven uses
   - **Example**: `mvn help:effective-pom`
   - **What it does**: Displays the complete POM with all inherited settings

### 16. **`mvn archetype:generate`**
   - **Purpose**: Generates a new project from an archetype (project template)
   - **When to use**: To create a new Maven project
   - **Example**: `mvn archetype:generate`
   - **What it does**: Interactive wizard to create a new project structure

### 17. **`mvn exec:java`**
   - **Purpose**: Executes a Java class with the classpath configured by Maven
   - **When to use**: To run a Java class directly (from main or test folder)
   - **Example**: `mvn exec:java -Dexec.mainClass="navaneeth.Calculator"`
   - **What it does**: Runs the specified main class with all dependencies on classpath
   - **Note**: For test classes, use `mvn test-compile exec:java`. The `classpathScope` in `pom.xml` can be set to `test` to include test classes.

### Command Options:

- **`-DskipTests`**: Skips running tests (but still compiles them)
  - Example: `mvn package -DskipTests`
  
- **`-Dmaven.test.skip=true`**: Skips compiling and running tests
  - Example: `mvn package -Dmaven.test.skip=true`

- **`-X` or `--debug`**: Runs in debug mode with detailed output
  - Example: `mvn compile -X`

- **`-q` or `--quiet`**: Runs in quiet mode with minimal output
  - Example: `mvn compile -q`

---

## How to Execute the Example Script

This project contains two example Java classes:
1. **`App.java`** - A simple "Hello World" program (located in `src/main/java/`)
2. **`Calculator.java`** - A calculator test script with arithmetic operations (located in `src/test/java/`)

**Important**: Since `Calculator.java` is in the test folder, you need to compile test classes before running it.

### Method 1: Using Maven Exec Plugin (Recommended)

The `exec-maven-plugin` is already configured in the `pom.xml` with `classpathScope` set to `test`:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>3.1.0</version>
            <configuration>
                <mainClass>navaneeth.Calculator</mainClass>
                <classpathScope>test</classpathScope>
            </configuration>
        </plugin>
    </plugins>
</build>
```

**To run the Calculator test script:**
```bash
mvn test-compile exec:java
```

**To run the App class (from main folder):**
```bash
mvn compile exec:java -Dexec.mainClass="navaneeth.App"
```

### Method 2: Compile and Run Manually

**For Calculator (test script):**

**Step 1: Compile test classes**
```bash
mvn test-compile
```
This compiles all Java files in `src/test/java/` to `target/test-classes/`

**Step 2: Run using Java command**
```bash
java -cp target/test-classes:target/classes navaneeth.Calculator
```

**For App class (main folder):**

**Step 1: Compile the project**
```bash
mvn compile
```
This compiles all Java files in `src/main/java/` to `target/classes/`

**Step 2: Run using Java command**
```bash
java -cp target/classes navaneeth.App
```

### Method 3: Package and Run the JAR

**Note**: The packaged JAR typically only includes classes from `src/main/java/`. Test classes are not included in the JAR by default. To run test scripts, use Method 1 or Method 2.

**For App class:**

**Step 1: Package the project**
```bash
mvn package
```
This creates a JAR file in the `target/` directory (e.g., `java_maven-1.0-SNAPSHOT.jar`)

**Step 2: Run the JAR file**
```bash
java -cp target/java_maven-1.0-SNAPSHOT.jar navaneeth.App
```

### Method 4: Create an Executable JAR (Fat JAR)

To create a JAR that includes all dependencies and can be run directly, you need to configure the `maven-shade-plugin` or `maven-assembly-plugin` in `pom.xml`.

### Step-by-Step Execution Guide:

#### Running the Calculator Test Script:

1. **Navigate to the project directory:**
   ```bash
   cd java_maven
   ```

2. **Compile test classes:**
   ```bash
   mvn test-compile
   ```
   Expected output: `BUILD SUCCESS`
   
   **Note**: This compiles both main and test source code. The Calculator class is in the test folder.

3. **Run the Calculator class:**
   ```bash
   mvn exec:java
   ```
   Or explicitly specify the class:
   ```bash
   mvn exec:java -Dexec.mainClass="navaneeth.Calculator"
   ```

   Expected output:
   ```
   === Simple Calculator Test Script ===
   
   Number 1: 20
   Number 2: 5
   
   Addition: 20 + 5 = 25
   Subtraction: 20 - 5 = 15
   Multiplication: 20 * 5 = 100
   Division: 20 / 5 = 4.0
   ```

#### Running the App Example:

**Method 1: Direct Java execution (Recommended)**
```bash
mvn compile
java -cp target/classes navaneeth.App
```

**Method 2: Using Maven Exec Plugin (PowerShell)**
```powershell
mvn compile
mvn exec:java `-Dexec.mainClass=navaneeth.App` `-Dexec.classpathScope=compile`
```

**Method 3: Using Maven Exec Plugin (Command Prompt / Bash)**
```bash
mvn compile
mvn exec:java -Dexec.mainClass=navaneeth.App -Dexec.classpathScope=compile
```

Expected output:
```
Hello World!
```

**Note**: The `-Dexec.classpathScope=compile` is required because the default exec plugin configuration is set to use the test classpath (for Calculator). In PowerShell, use backticks (`) to escape the `-D` flags.

### Troubleshooting:

1. **"Could not find or load main class"**
   - For test scripts: Make sure you've compiled test classes: `mvn test-compile`
   - For main classes: Make sure you've compiled: `mvn compile`
   - Check that the class name matches exactly (case-sensitive)

2. **"exec-maven-plugin not found"**
   - The plugin is already configured in `pom.xml` with `classpathScope` set to `test`

3. **"No goals have been specified"**
   - Make sure you're using a valid Maven goal (compile, test-compile, test, package, etc.)

4. **"ClassNotFoundException" when running test scripts**
   - Make sure you use `mvn test-compile` before running test scripts
   - The `classpathScope` in `pom.xml` should be set to `test` for test scripts

5. **Cannot run App.java from main folder**
   - Use direct Java execution: `mvn compile` then `java -cp target/classes navaneeth.App`
   - Or override classpathScope: `mvn exec:java -Dexec.mainClass=navaneeth.App -Dexec.classpathScope=compile`
   - In PowerShell, escape the `-D` flags with backticks: `` `-Dexec.mainClass=navaneeth.App` ``

---

## Maven Build Lifecycle

Maven has three built-in build lifecycles:

1. **default**: Handles project deployment
   - validate → compile → test → package → verify → install → deploy

2. **clean**: Handles project cleaning
   - pre-clean → clean → post-clean

3. **site**: Handles site documentation
   - pre-site → site → post-site → site-deploy

When you run a command like `mvn package`, Maven executes all phases up to and including the `package` phase.

---

## Summary

Maven is an essential tool for Java development that:
- Manages dependencies automatically
- Standardizes project structure
- Automates build processes
- Integrates with IDEs and CI/CD tools
- Simplifies project management

By following Maven conventions and using standard commands, you can efficiently build, test, and deploy Java applications.

---

**Happy Coding with Maven! 🚀**

