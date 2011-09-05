package bootstrap.liftweb
import net.liftweb.sitemap.SiteMap
import net.liftweb.sitemap.Menu
import net.liftweb.http.LiftRules

class Boot {
  def boot {
    LiftRules.addToPackages("test")
    def sitemap(): SiteMap = SiteMap(Menu.i("Home") / "index")
    LiftRules.setSiteMapFunc(() => sitemap())
  }
}