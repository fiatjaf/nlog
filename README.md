nlog
====

### Installation

```scala
libraryDependencies += "com.fiatjaf" %%% "nlog" % "0.1.0"
```

### Simple usage

```scala
val logger = new nlog.Logger()

val a = 23
var b = false
logger.debug.item("a", a).item(b).msg("something happened")
```

This will print a somewhat-readable message to STDERR.

### Advanced usage

You can define your own printer and log level threshold.

```scala
val logger = new nlog.Logger {
  override def print(
    level: nlog.Level,
    items: List[(String, Any) | Any],
    msg: String
  ): Unit = {
    val levelName = level.getClass.getSimpleName.toUpperCase()
    System.err.println(s"[$levelName] | `$msg` | $items")
  }
  override val threshold = nlog.Debug
}
```
