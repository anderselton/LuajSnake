local Vector = luajava.bindClass("cx.snake.util.Vector2d")
local Color = luajava.bindClass("java.awt.Color")

function createSnake(x,y,dir)
    local snake = gameController:createSnake(x, y, dir )
    local snakeController = gameController:createSnakeController()
    snakeController:init(snake, game:getGameboard())
    game:addSnakeController(snakeController)
    return snake, snakeController
end

local snake = createSnake(10, 10, Vector.Down )
snake:setColor(Color.BLUE)

snake:addEventListener(luajava.createProxy("cx.snake.game.event.GameEventListener", {
	OnEvent = function(e)
	    print("Got event : " .. e:getType())
	end,
}))


game:start()