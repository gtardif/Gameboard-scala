package bootstrap.liftweb

import net.liftweb.sitemap._
import net.liftweb.http._
import gameboard.comet.GameWebService

class Boot {
  def boot {
    LiftRules.addToPackages("gameboard")

    val entries = List(
      Menu.i("Home") / "index",
      Menu.i("new P4") / "standaloneGame")

    LiftRules.setSiteMap(SiteMap(entries: _*))
    LiftRules.dispatch.append(GameWebService)

    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))
  }
}