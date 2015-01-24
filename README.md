# Reflector
---
[![Build Status](https://travis-ci.org/zabawaba99/reflector.svg?branch=master)](https://travis-ci.org/zabawaba99/reflector)

---
## Purpose

Built with the intention of making reflective programming simpler.

##### Under Development
The api/code may or may not change radically within the next upcomming weeks. 

## Usage

### With Maven

Add the snapshot repository and the dependency to you pom.xml file

```
<repositories>
	<repository>
		<id>reflector-mvn-repo</id>
		<url>https://raw.github.com/zabawaba99/reflector/mvn-repo/</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories>

<dependencies>
	<dependency>
		<groupId>com.zabawaba99</groupId>
		<artifactId>reflector</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</dependency>
</dependencies>
```

### Gradle

Coming soon

### Manual install

For the time being, you can download the jar in [mvn-repo](https://github.com/zabawaba99/reflector/tree/mvn-repo/com/zabawaba99/reflector) 
branch and add the jar to your project's classpath.

---

After adding Reflector to your project, just import the package
and start using.

```java
package com.example

import com.zabawaba.reflector;

public static void main(String[] args){
	HashSet<Methods> methods = ReflectionUtil.getMethods(Foo.class);
	// ... do something with the set of methods 
}
```

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b new-feature`)
3. Commit your changes (`git commit -am 'Some cool reflection'`)
4. Push to the branch (`git push origin new-feature`)
5. Create new Pull Request
