-- denne kommer i system.out!

print 'Initializing script!'

-- called each frame by event dispatcher.
function RunFrame(controller)
    snake = controller:getSnake()
    food = controller:getGameboard():getFood()
    food_distance = 0
    if food ~= nil then
        food_distance = snake:getHeadPos():distance(food:getPos())
    end
    print ('RunFrame - direction is: ' .. snake:toString() .. ' distance to Food: ' .. food_distance)
end