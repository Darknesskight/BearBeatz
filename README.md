**Compiling:**  
	Command Line:  Run this in the root path of the project, make sure to create a bin/ directory there.  

*Windows:*
	<pre>
```javac -d "bin/" -classpath "lib/slick.jar;lib/lwjgl.jar" src/org/tunnelsnakes/Game.java
	src/org/tunnelsnakes/attributes/*.java src/org/tunnelsnakes/entities/*.java
	src/org/tunnelsnakes/geom/*.java src/org/tunnelsnakes/menus/*.java
	src/org/tunnelsnakes/states/*.java src/org/tunnelsnakes/ui/*.java src/org/tunnelsnakes/util/*.java``` </pre>
*Unix:*
	<pre>
```javac -d "bin/" -classpath "lib/slick.jar:lib/lwjgl.jar" src/org/tunnelsnakes/Game.java
	src/org/tunnelsnakes/attributes/*.java src/org/tunnelsnakes/entities/*.java
	src/org/tunnelsnakes/geom/*.java src/org/tunnelsnakes/menus/*.java
	src/org/tunnelsnakes/states/*.java src/org/tunnelsnakes/ui/*.java src/org/tunnelsnakes/util/*.java``` </pre>
**Running:**  
	Command Line:  Run this in the root path of the project  
  
*Windows:*
	<pre>
```java -cp "lib/*;bin/" -Djava.library.path="natives/" org.tunnelsnakes.Game``` </pre> 
*Unix:*  
	<pre>
```java -cp "lib/*:bin/" -Djava.library.path="natives/" org.tunnelsnakes.Game``` </pre>