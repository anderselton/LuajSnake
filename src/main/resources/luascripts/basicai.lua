local Vector = luajava.bindClass("cx.snake.util.Vector2d")
local Color = luajava.bindClass("java.awt.Color")

function createSnake(x,y,dir)
    local snake = gameController:createSnake(x, y, dir )
    local snakeController = gameController:createSnakeController()
    snakeController:init(snake, game:getGameboard())
    game:addSnakeController(snakeController)
    return snake, snakeController
end

local snake, snakeController = createSnake(10, 10, Vector.Down )
snake:setColor(Color.BLUE)

local snake2, snakeController2 = createSnake(50, 10, Vector.Down )
snake2:setColor(Color.GREEN)


function isMoveSafe(controller, direction)
    pos = controller:getSnake():getHeadPos()
    newPos = pos:add(direction)
    return controller:getGameboard():testCollision(newPos) == false
end

allDirections = {Vector.Left, Vector.Right, Vector.Up, Vector.Down}

function calculateDirectionToFood(controller)
    local food = controller:getGameboard():getFood()
    local snake = controller:getSnake()
    local wantedDirection = {}

    if food~=nil then
        local dxPos = snake:getHeadPos():getX() - food:getPos():getX()
        if dxPos < 0 then
            table.insert(wantedDirection, Vector.Right)
        elseif dxPos > food:getSize() then
            table.insert(wantedDirection, Vector.Left)
        end

        local dyPos = snake:getHeadPos():getY() - food:getPos():getY()
        if dyPos < 0 then
            table.insert(wantedDirection, Vector.Down)
        elseif dyPos > food:getSize() then
            table.insert(wantedDirection, Vector.Up)
        end

        return wantedDirection
    end

    return allDirections
end

snake:addEventListener(luajava.createProxy("cx.snake.game.event.GameEventListener", {
	OnEvent = function(e)
	snake = snakeController:getSnake()
	    print("Got event : " .. e:getType())

                local directionsToFood = calculateDirectionToFood(snakeController)
                for i=1, #directionsToFood,1 do
                    local dir = directionsToFood[i]
                    print("wanted: " .. dir:toString())
                    if isMoveSafe(snakeController, dir) then
                        print("Safe move: " .. dir:toString());
                        snake:setDirection(dir)
                        break;
                    end
                end
                if not isMoveSafe(snakeController, snake:getDirection()) then
                    for i=1, #allDirections,1 do
                        local dir = allDirections[i]
                        if isMoveSafe(snakeController, dir) then
                            snake:setDirection(dir)
                            break;
                        end
                    end
                end


	end,
}))


game:start()
