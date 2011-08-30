package gameboard

import org.specs.mock.Mockito
import org.specs.runner.JUnit
import gameboard.Side._
import org.specs.SpecificationWithJUnit

class P4BotTest extends SpecificationWithJUnit with Mockito {
  val mockBoard = mock[P4Board]
  val mockGame = mock[P4Game]

  val bot = new P4Bot(RED)

  "P4 bot" should {
    "start game in middle column" in {
      mockBoard.player returns RED

      bot.updateBoard(mockBoard, mockGame)

      there was one(mockGame).play(3, bot)
    }
    
    "play only when it is its turn" in {
      mockBoard.player returns YELLOW
      
      bot.updateBoard(mockBoard, mockGame)

      there was no(mockGame).play(3, bot)
    }
  }
}