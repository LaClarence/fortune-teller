# Graalvm - Fortune Teller demo

The fortune teller demo is based on this [article](https://graalvm.github.io/native-build-tools/latest/end-to-end-maven-guide.html)
using Graalvm:
* Java(TM) SE Runtime Environment Oracle GraalVM 25.0.2+10.1 (build 25.0.2+10-LTS-jvmci-b01)

## build package (ie jar)

```
mvn clean package
```
shall build jars:
* target/fortune-teller-1.1-SNAPSHOT.jar
* target/fortune-teller-1.1-SNAPSHOT-jar-with-dependencies.jar

launch command:
```
java -jar ./target/fortune-teller-1.0-SNAPSHOT-jar-with-dependencies.jar
```
shall print s.l.o.w.l.y a fortune like:

**Don’t let people drive you crazy when you know it’s in walking distance.**

## build native

```
mvn -Pnative -Dagent exec:exec@java-agent
```
shall print a fortune 👍

When building native image using maven command line
```
mvn -DskipTests=true -Pnative -Dagent package
or
mvn -Pnative package
```

The build is ok
Build artifacts:
```
/xxx/fortune-teller/target/fortune (executable)
========================================================================================================================
Finished generating 'fortune' in 29,8s.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

Launching executable fortune lead to this message:
```
./target/fortune
Learn to pause -- or nothing worthwhile can catch up to you.
```

