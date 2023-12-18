# look up tables for how to change direction and what
# available moves there are
CHANGE_MAP = {"u": (-1, 0), "d": (1, 0), "l": (0, -1), "r": (0, 1)}
MOVES = {"u": "url", "d": "drl", "l": "lud", "r": "rud"}


def get_possible_directions(steps, direction):
    directions = MOVES[direction]
    if steps < 3:
        return directions
    else:
        directions = directions[1:]
    return directions


def get_possible_directions_for_part_2(steps, direction):
    directions = MOVES[direction]
    if steps < 4:
        directions = [direction]
    elif steps < 10:
        return directions
    else:
        directions = directions[1:]
    return directions


def calculate_path(paths, grid):

    # build our initial paths to check
    paths_to_check = []
    for k in paths.keys():
        paths_to_check.append(k)

    while len(paths_to_check) != 0:
        next_paths_to_check = []
        for path in paths_to_check:
            # get all info about the path we are checking
            direction = path[0]
            steps = path[1]
            r = path[2]
            c = path[3]

            # get all next possible directions for our current position
            possible_directions = get_possible_directions_for_part_2(steps, direction)

            # go through each direction we can go
            for possible_direction in possible_directions:
                change = CHANGE_MAP[possible_direction]
                next_row = r + change[0]
                next_col = c + change[1]

                # if we are out of bounds, just go to next direction
                if (next_row, next_col) not in grid:
                    continue

                # if we are staying in the same direction, add one to steps
                # otherwise, reset steps to 1
                if possible_direction == direction:
                    next_steps = steps + 1
                else:
                    next_steps = 1

                # create the next position that we are going to
                next_key = (possible_direction, next_steps, next_row, next_col)

                # calculate the heat loss when moving
                # current location + next location
                next_heat_loss = paths[(direction, steps, r, c)] + grid[(next_row, next_col)]

                # if we haven't visited this next location OR we found a better path
                # replace the heat loss and append it to the paths to check
                if next_key not in paths or paths[next_key] > next_heat_loss:
                    paths[next_key] = next_heat_loss
                    next_paths_to_check.append(next_key)

            paths_to_check = next_paths_to_check


def parse(file):
    all_lines = file.read()
    lines = all_lines.splitlines()
    grid = {}
    heat_losses = {}
    row = -1
    col = -1
    for row, line in enumerate(lines):
        for col, c in enumerate(line):
            grid[(row, col)] = int(c)
    end_pos = (row, col)
    heat_losses[("r", 10, 0, 0)] = 0
    heat_losses[("d", 10, 0, 0)] = 0
    return end_pos, grid, heat_losses


f = open("day17_input", "r")
final_location, full_grid, path_list = parse(f)
calculate_path(path_list, full_grid)

lowest_heat = 10000000000
for item in path_list:
    if item[2] == final_location[0] and item[3] == final_location[1]:
        if path_list[item] < lowest_heat and item[1] > 3:
            lowest_heat = path_list[item]

print(lowest_heat)

