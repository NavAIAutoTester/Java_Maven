# Java Maven Project

A basic Java Maven project demonstrating Maven usage with example code.

## Project Information

- **Group ID**: navaneeth
- **Artifact ID**: java_maven
- **Version**: 1.0-SNAPSHOT
- **Packaging**: jar

## Quick Start

### Prerequisites
- Java JDK (8 or higher)
- Apache Maven (3.x)

## Maven Installation

### Verify Java Installation

First, ensure Java is installed on your system:

```bash
java -version
javac -version
```

If Java is not installed, download and install it from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/).

### Install Maven

#### Windows

**Method 1: Using Chocolatey (Recommended)**
```powershell
choco install maven
```

**Method 2: Manual Installation**

1. Download Maven from [Apache Maven Downloads](https://maven.apache.org/download.cgi)
   - Download the `apache-maven-3.x.x-bin.zip` file

2. Extract the zip file to a directory (e.g., `C:\Program Files\Apache\maven`)

3. Add Maven to your system PATH:
   - Open **System Properties** → **Environment Variables**
   - Under **System Variables**, find and select **Path**, then click **Edit**
   - Click **New** and add: `C:\Program Files\Apache\maven\bin`
   - Click **OK** to save

4. Set `JAVA_HOME` environment variable (if not already set):
   - Create a new **System Variable** named `JAVA_HOME`
   - Set the value to your JDK installation path (e.g., `C:\Program Files\Java\jdk-21`)

5. Verify installation:
   ```cmd
   mvn -version
   ```

#### macOS

**Method 1: Using Homebrew (Recommended)**
```bash
brew install maven
```

**Method 2: Manual Installation**

1. Download Maven from [Apache Maven Downloads](https://maven.apache.org/download.cgi)
   - Download the `apache-maven-3.x.x-bin.tar.gz` file

2. Extract and move to a standard location:
   ```bash
   tar -xzf apache-maven-3.x.x-bin.tar.gz
   sudo mv apache-maven-3.x.x /usr/local/apache-maven
   ```

3. Add to PATH by editing `~/.zshrc` or `~/.bash_profile`:
   ```bash
   export PATH="/usr/local/apache-maven/bin:$PATH"
   export JAVA_HOME=$(/usr/libexec/java_home)
   ```

4. Reload your shell configuration:
   ```bash
   source ~/.zshrc  # or source ~/.bash_profile
   ```

5. Verify installation:
   ```bash
   mvn -version
   ```

#### Linux (Ubuntu/Debian)

**Method 1: Using apt (Recommended)**
```bash
sudo apt update
sudo apt install maven
```

**Method 2: Manual Installation**

1. Download Maven from [Apache Maven Downloads](https://maven.apache.org/download.cgi)
   - Download the `apache-maven-3.x.x-bin.tar.gz` file

2. Extract and move to `/opt`:
   ```bash
   tar -xzf apache-maven-3.x.x-bin.tar.gz
   sudo mv apache-maven-3.x.x /opt/maven
   ```

3. Add to PATH by editing `~/.bashrc` or `~/.profile`:
   ```bash
   export PATH="/opt/maven/bin:$PATH"
   export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64  # Adjust path as needed
   ```

4. Reload your shell configuration:
   ```bash
   source ~/.bashrc  # or source ~/.profile
   ```

5. Verify installation:
   ```bash
   mvn -version
   ```

### Verify Maven Installation

After installation, verify that Maven is correctly installed:

```bash
mvn -version
```

You should see output similar to:
```
Apache Maven 3.9.x
Maven home: /path/to/maven
Java version: 17.x.x, vendor: ...
Java home: /path/to/java
Default locale: en_US, platform encoding: UTF-8
OS name: "windows", version: "10.0", arch: "amd64", family: "windows"
```

### Troubleshooting Installation

1. **"mvn: command not found"**
   - Verify that Maven's `bin` directory is in your PATH
   - Restart your terminal/command prompt after adding to PATH

2. **"JAVA_HOME is not set correctly"**
   - Set the `JAVA_HOME` environment variable to your JDK installation directory
   - Ensure there's no trailing slash in the path

3. **"UnsupportedClassVersionError"**
   - Ensure you're using Java 8 or higher
   - Check your Java version: `java -version`

### Compile the Project
```bash
mvn compile
```

### Run the Calculator Test Script
```bash
mvn test-compile exec:java
```
Note: Calculator.java is located in the test folder, so you need to compile test classes first.

### Run the App Example (Hello World)

**Method 1: Direct Java execution (Recommended - Simplest)**
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
```cmd
mvn compile
mvn exec:java -Dexec.mainClass=navaneeth.App -Dexec.classpathScope=compile
```

**Note**: 
- The `-Dexec.classpathScope=compile` is required because the default exec plugin configuration uses the test classpath (for Calculator).
- In PowerShell, use backticks (`) before the `-D` flags to escape them properly.
- Method 1 (direct Java execution) is the simplest and most reliable approach.

### Run Tests
```bash
mvn test
```

### Package the Project
```bash
mvn package
```

## Project Structure

```
java_maven/
├── pom.xml                          # Maven configuration file
├── README.md                         # This file
├── MAVEN_GUIDE.md                    # Comprehensive Maven guide
└── src/
    ├── main/
    │   └── java/
    │       └── navaneeth/
    │           └── App.java          # Hello World example
    └── test/
        └── java/
            └── navaneeth/
                ├── AppTest.java      # Unit tests
                └── Calculator.java   # Calculator test script
```

## Example Classes

### 1. App.java
A basic "Hello World" application located in `src/main/java/`.

### 2. Calculator.java
A test script located in `src/test/java/` demonstrating basic arithmetic operations:
- Addition
- Subtraction
- Multiplication
- Division

**Note**: Calculator.java is a test script, so it's placed in the test folder. To run it, use `mvn test-compile exec:java`.

## Documentation

For detailed information about Maven, see **[MAVEN_GUIDE.md](MAVEN_GUIDE.md)** which covers:
- What is Maven?
- Maven uses and benefits
- Folder structure explanation
- Common Maven commands with detailed explanations
- How to execute examples using Maven commands

## Common Commands Reference

| Command | Description |
|---------|-------------|
| `mvn clean` | Remove compiled files |
| `mvn compile` | Compile source code |
| `mvn test` | Run tests |
| `mvn package` | Create JAR file |
| `mvn install` | Install to local repository |
| `mvn clean install` | Clean and install |
| `mvn exec:java` | Run main class (use `test-compile` first for test scripts) |
| `mvn dependency:tree` | Display dependency tree |
| `mvn dependency:resolve` | Download all dependencies |
| `mvn versions:display-dependency-updates` | Find latest versions of dependencies |
| `mvn versions:use-latest-versions` | Update all dependencies to latest versions (use with caution) |

## License

This is a sample project for educational purposes.

