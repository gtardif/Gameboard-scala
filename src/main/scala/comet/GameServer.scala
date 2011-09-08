package gameboard.comet

import net.liftweb._
import http._
import actor._
import gameboard._

/**
 * A singleton that provides chat features to all clients.
 * It's an Actor so it's thread-safe because only one
 * message will be processed at once.
 */
object GameServer extends LiftActor with ListenerManager {
  private var msgs = Vector("Red starts playing") // private state
  
  class WebPlayer extends Player {
    val side = Side.RED
    
    override def updateBoard(board :P4Board , game : P4Game) {
    	if (game.moves.isEmpty) return
    	val lastMove = game.moves.last
    	newMessage(lastMove._2.toString() + " plays " + lastMove._1)
    }
    
    override def newMessage(message:String) {
      GameServer.newMessage(message)
    }
  }
  
  val webPlayer = new WebPlayer()
  val botPlayer = new P4Bot(Side.YELLOW)
  val game = new P4Game(List(webPlayer, botPlayer))
  game.start()
  
  /**
   * When we update the listeners, what message do we send?
   * We send the msgs, which is an immutable data structure,
   * so it can be shared with lots of threads without any
   * danger or locking.
   */
  def createUpdate = msgs

  /**
   * process messages that are sent to the Actor.  In
   * this case, we're looking for Strings that are sent
   * to the ChatServer.  We append them to our Vector of
   * messages, and then update all the listeners.
   */
  override def lowPriority = {
    case column: Int => game.play(column, webPlayer)
  }
  
  private def newMessage(newMessage: java.lang.String): Unit = {
    msgs :+= newMessage; 
    updateListeners()
  }
}
