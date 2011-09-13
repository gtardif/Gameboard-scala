package bootstrap.liftweb

import net.liftweb.sitemap._
import net.liftweb.http._

class Boot {
  def boot {
    LiftRules.addToPackages("gameboard")

    val entries = List(
      Menu.i("Home") / "index",
      Menu.i("New Game") / "newGame" submenus(
          Menu.i("Play against Bot") / "playBot",
          Menu.i("Start 2 players game") / "start2Players",
          Menu.i("Join 2 players game") / "join2Players"
          ), 
      Menu.i("new P4") / "standaloneGame" 
      )

    LiftRules.setSiteMap(SiteMap(entries: _*))

    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))
  }
}