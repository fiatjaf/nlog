ThisBuild / scalaVersion        := "3.1.3"
ThisBuild / organization        := "com.fiatjaf"
ThisBuild / homepage            := Some(url("https://github.com/fiatjaf/nlog"))
ThisBuild / licenses            += License.MIT
ThisBuild / developers          := List(tlGitHubDev("fiatjaf", "fiatjaf"))

ThisBuild / tlBaseVersion       := "0.4"
ThisBuild / tlSonatypeUseLegacyHost := false

ThisBuild / scalaVersion := "3.1.3"

lazy val root = tlCrossRootProject.aggregate(core)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .in(file("."))
  .settings(
    name := "nlog",
    description := "Simple and naïve logging library with no dependencies and no batteries included (it will just print to stderr if you don't say otherwise).",
  )
