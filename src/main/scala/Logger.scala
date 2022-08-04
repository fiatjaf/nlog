package nlog

sealed trait Level
case object Debug extends Level
case object Info extends Level
case object Warn extends Level
case object Err extends Level

object Levels {
  val severity = Array(Debug, Info, Warn, Err)
  def shouldShow(threshold: Level, logLevel: Level): Boolean =
    severity.indexOf(logLevel) >= severity.indexOf(threshold)
}

type Items = List[(String, Any) | Any]

class Attacher(parent: Logger) {
  var items: Items = parent.getItems
  def item(label: String, value: Any) = {
    items = items :+ (label, value)
    this
  }
  def item(value: Any) = {
    items = items :+ value
    this
  }

  def logger(): Logger = new Logger(items) {
    override def print(level: Level, items: Items, msg: String): Unit =
      parent.print(level, items, msg)
    override val threshold = parent.threshold
  }
}

class Logger(val items: Items = List.empty) {
  def print(level: Level, items: Items, msg: String): Unit = {
    val lvl = level.getClass.getSimpleName.toUpperCase()
    val its =
      items
        .map {
          case (l: String, it: Any) => s"[$l]=$it"
          case it                   => s"{$it}"
        }
        .mkString(" ")
    val sep = if items.size > 0 then " -- " else ""
    System.err.print(level, s"$lvl ${msg}${sep}${its}")
  }

  val threshold: Level = Debug

  def debug = new Log(this, Debug)
  def info = new Log(this, Info)
  def warn = new Log(this, Warn)
  def err = new Log(this, Err)
  def attach = new Attacher(this)

  def getItems = items
  def maybePrint(logLevel: Level, items: Items, msg: String): Unit =
    if (Levels.shouldShow(threshold, logLevel)) print(logLevel, items, msg)
}

class Log(parent: Logger, level: Level = Debug) {
  var items: Items = parent.getItems
  def item(label: String, value: Any) = {
    items = items :+ (label, value)
    this
  }
  def item(value: Any) = {
    items = items :+ value
    this
  }

  def msg(text: String): Unit =
    parent.maybePrint(level, items, text)
}
