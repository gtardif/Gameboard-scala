package bootstrap.liftweb

import net.liftweb.sitemap._
import net.liftweb.http._

class Boot {
  def boot {
    LiftRules.addToPackages("test")

    val entries = List(
      Menu.i("Home") / "index",
      Menu.i("New Game") / "newGame")

    LiftRules.setSiteMap(SiteMap(entries: _*))

    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))
  }
}