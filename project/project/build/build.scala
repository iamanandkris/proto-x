import sbt.Resolver

//def managedStyle = ManagedStyle.Maven
lazy val publishTo = Resolver.sftp("My Maven Repo", "maven.example.org", "/var/www/maven/html")
