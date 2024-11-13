# Read Me First
The following was discovered as part of building this project:

* The original package name 'lk.anan.ri.data-viewer' is invalid and this project uses 'lk.anan.ri.data_viewer' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.5/maven-plugin/build-image.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/3.3.5/reference/web/reactive.html)
* [Embedded LDAP Server](https://docs.spring.io/spring-boot/3.3.5/reference/data/nosql.html#data.nosql.ldap.embedded)
* [Spring LDAP](https://docs.spring.io/spring-boot/3.3.5/reference/data/nosql.html#data.nosql.ldap)
* [Java Mail Sender](https://docs.spring.io/spring-boot/3.3.5/reference/io/email.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.
```

<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-dependency-plugin</artifactId>
        <version>3.6.0</version>   

        <executions>
          <execution>
            <id>unpack-sources</id>   

            <phase>generate-test-sources</phase>
            <goals>
              <goal>unpack</goal>
            </goals>
            <configuration>
              <outputDirectory>${project.build.directory}/generated-sources/java</outputDirectory>   

              <includeGroupIds>com.yourcompany</includeGroupIds>
              <includeArtifactIds>other-project</includeArtifactIds>
              <classifier>sources</classifier>
              <includeTypes>jar</includeTypes>
            </configuration>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>build-helper-maven-plugin</artifactId>   

        <version>3.3.0</version>
        <executions>
          <execution>
            <id>add-source</id>
            <phase>generate-test-sources</phase>   

            <goals>
              <goal>add-source</goal>
            </goals>
            <configuration>
              <sources>
                <source>${project.build.directory}/generated-sources/java</source>
              </sources>
            </configuration>
          </execution>
        </executions>   

      </plugin>
    </plugins>
  </build>   

  ...
</project>
```
```
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-dependency-plugin</artifactId>
        <version>3.6.0</version>   

        <executions>
          <execution>
            <id>unpack-sources</id>   

            <phase>generate-test-sources</phase>
            <goals>
              <goal>unpack</goal>
            </goals>
            <configuration>
              <outputDirectory>${project.build.directory}/generated-sources/java</outputDirectory>   

              <includeGroupIds>com.yourcompany</includeGroupIds>
              <includeArtifactIds>other-project</includeArtifactIds>
              <classifier>sources</classifier>
              <includeTypes>jar</includeTypes>
              <includes>
                <include>**/SomeClass.java</include>
                <include>com/yourcompany/otherproject/utils/*.java</include>
              </includes>
              <excludes>
                <exclude>**/Test*.java</exclude>
              </excludes>
            </configuration>
          </execution>
        </executions>
      </plugin>
      ...
    </plugins>
  </build>
  ...
</project>   
```