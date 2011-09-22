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
  def blindGameComet = <lift:comet type="PlayerView" name={nameParam}/>
  def gameComet = <lift:comet type="UIUpdater" name={nameParam}/>
  
  def playScript = <script>
		var mySide = 'RED';
		var myGame = '{nameParam}';
		
		play = function(column) {{
			$.ajax({{url : '/P4/play/' + myGame + '/' + mySide + '/' + column}});
		}}

		newGame = function(name) {{
			$.ajax({{url : '/P4/newGame/' + name}});
		}}

  		join = function(name) {{
			$.ajax({{url : '/P4/join/' + name,
  					success : setSide
  			}});
  		}}

  		setSide = function(data) {{
  			mySide = JSON.parse(data)
  		}}
	</script>
  
  private def nameParam: String = S.param("name") openOr "default"
}
