crossScalaVersions in Global := Seq("2.13.1", "2.12.7", "2.11.12", "2.10.7")

scalaVersion in Global := crossScalaVersions.value.head

organization in Global := "com.avantstay"

lazy val library = project
  .settings(publishSettings: _*)
  .settings(
    name := "inflector",
    version := "1.12.0",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.8" % "test",
    publishArtifact := true,
    publishArtifact in Test := false
  )

lazy val publishSettings = Seq(
  pomExtra := <url>https://github.com/hypertino/inflector</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://opensource.org/licenses/BSD-3-Clause</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:hypertino/inflector.git</url>
      <connection>scm:git:git@github.com:hypertino/inflector.git</connection>
    </scm>
    <developers>
      <developer>
        <id>maqdev</id>
        <name>Magomed Abdurakhmanov</name>
        <url>https://github.com/maqdev</url>
      </developer>
      <developer>
        <id>hypertino</id>
        <name>Hypertino</name>
        <url>https://github.com/hypertino</url>
      </developer>
    </developers>,
  pgpSecretRing := file("./travis/script/ht-oss-private.asc"),
  pgpPublicRing := file("./travis/script/ht-oss-public.asc"),
  usePgpKeyHex("F8CDEF49B0EDEDCC"),
  pgpPassphrase := Option(System.getenv().get("oss_gpg_passphrase"))
    .map(_.toCharArray),
  publishMavenStyle := true,
  pomIncludeRepository := { _ =>
    false
  },
  publishTo := {
    if (isSnapshot.value)
      Some(
        "AvantStay Snapshots" at "https://maven.avantstay.rocks/repository/avantstay-snapshots/"
      )
    else
      Some(
        "AvantStay Releases" at "https://maven.avantstay.rocks/repository/avantstay-releases/"
      )
  },
  credentials += Credentials(Path.userHome / ".sbt" / "credentials")
)
lazy val `inflector-root` = project
  .in(file("."))
  .settings(publishSettings: _*)
  .aggregate(library)
  .settings(
    publish := {},
    publishLocal := {},
    publishArtifact in Test := false,
    publishArtifact := false
  )
