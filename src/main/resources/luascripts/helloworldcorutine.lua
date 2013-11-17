-- This snake simulation runs with callback happening in a coroutine.
-- A corutine is a function you return from, but it can be resumed from the point you returned.
-- Makes writing logic pretty simple since you keep the stack from the previous frame.

if coroutineTable == nil then
	coroutineTable = {}
end

function scheduleCoroutine(coroutineToSchedule)
	local entry = {}
	entry.coroutine = coroutineToSchedule;
	entry.nextTick = 0;
	table.insert(coroutineTable, entry);
end

function RunFrame(controller)
	for i=#coroutineTable,1,-1 do
		entry = coroutineTable[i]
		entry.nextTick = entry.nextTick -1;
		if entry.nextTick <= 0 then
			runOk, returnedValues = coroutine.resume(entry.coroutine, controller)
			returnedValues = returnedValues or {}
            local keep = returnedValues.keep or true;
			if not runOk or keep~=true then
				if not runOk then print("error: " .. returnedValues); end
				table.remove(coroutineTable, i);
			end
			if returnedValues.nextTick ~=nil then
				entry.nextTick = returnedValues.nextTick;
			end

		end
	end
end

local Vector = luajava.bindClass("cx.snake.util.Vector2d")

local co = coroutine.create(function (controller)
    local snake = controller:getSnake()
    while snake:isAlive() do
        if snake:getDirection() == Vector.Down then
            coroutine.yield({ nextTick=snake:getSize()})
            controller:moveRight();
        end

        if snake:getDirection() == Vector.Right then
            coroutine.yield({ nextTick=snake:getSize()})
            controller:moveUp();
        end

        if snake:getDirection() == Vector.Up then
            coroutine.yield({ nextTick=snake:getSize()})
            controller:moveLeft();
        end

        if snake:getDirection() == Vector.Left then
            coroutine.yield({ nextTick=snake:getSize()})
            controller:moveDown();
        end
    end
	return false
end)

scheduleCoroutine(co)