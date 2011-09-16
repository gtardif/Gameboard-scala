package gameboard.snippet

import net.liftweb._
import net.liftweb.util.Helpers._
import http._
import js._
import JsCmds._
import JE._
import scala.xml.NodeSeq

import gameboard.comet.GameServer

object P4Snippets {
  def testComet = <lift:comet type="Tick" name={nameParam}/>
  def blindGameComet = <lift:comet type="PlayerView" name={nameParam}/>
  def gameComet = <lift:comet type="UIUpdater" name={nameParam}/>
  
  def playScript = <script>
		play = function(column) {{
			$.ajax({{url : '/P4/play/{nameParam}/' + column}});
		}}
	</script>
  
  private def nameParam: String = S.param("name") openOr "default"
}
