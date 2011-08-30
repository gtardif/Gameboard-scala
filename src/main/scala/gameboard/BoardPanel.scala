package gameboard

import scala.swing._
import java.awt.image.BufferedImage                                           
import java.io.File                                                           
import javax.imageio.ImageIO
import swing.event._

class BoardPanel  (var board :P4Board, val player : HumanPlayer) extends Component {
  private val redPawn = ImageIO.read(new File("src/main/resources/steak.png"))      
  private val yellowPawn = ImageIO.read(new File("src/main/resources/peach.png"))      
  private val boardImage = ImageIO.read(new File("src/main/resources/concert-prince.png"))
  
  private var game : P4Game = null
  
  override def paintComponent(g:Graphics2D) = { 
    g.drawImage(boardImage, 0, 0, null) 
    (board.columns zipWithIndex) foreach(paintColumn(g, _))
  } 
  
  private def paintColumn(g:Graphics2D, columnWithIndex : (List[Side.Side], Int)):Unit = {
    val (column, index : Int) = columnWithIndex
    (column zipWithIndex) foreach(paintCell(g, index, _))
  }
  
  private def paintCell(g:Graphics2D, columnIndex : Int, cellWithIndex : (Side.Side, Int)):Unit = {
    val (cell, rawIndex) = cellWithIndex
    val image = if (cell == Side.RED) redPawn else yellowPawn
    g.drawImage(image, columnIndex * image.getWidth(), (6-rawIndex -1) * image.getHeight(), null)
  }
   
  def updateBoard(newBoard : P4Board, game:P4Game) {
    this.game = game
    board = newBoard
    this.repaint()
  }
  
  override def preferredSize = { new Dimension(boardImage.getWidth(), boardImage.getHeight()) }
  
  reactions += {
    case MouseClicked(source, point, modifiers, clicks, triggersPopup) => {
      game.play((point.getX() / redPawn.getWidth()).toInt, player)
    }
  }
  
  listenTo(this.Mouse.clicks)
}