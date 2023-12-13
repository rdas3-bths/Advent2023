from functools import cache

# def check_garden(garden, sequence):
#
#     for i in range(len(garden)):
#         if garden[i] == "?":
#             return False
#         if garden[i] == ".":
#             garden = replace_at_index(garden, " ", i)
#
#     garden = ' '.join(garden.split())
#     garden_list = garden.split(" ")
#     sequence_check = []
#     for g in garden_list:
#         sequence_check.append(len(g))
#
#     if len(sequence_check) != len(sequence):
#         return False
#     else:
#         for i in range(len(sequence_check)):
#             if sequence_check[i] != sequence[i]:
#                 return False
#
#     return True
#
#
# def replace_at_index(string, new_char, index):
#     string_list = list(string)
#     string_list[index] = new_char
#     new_string = "".join(string_list)
#     return new_string
#
#
# # answer = 0
# # base case:
# # if garden has no ? in it, check_garden
#     # if true return answer += 1
#     # if false return answer += 0
#
# # otherwise:
#     # for item in [ ".", "#" ]
#         # replace current position with item
#         # call function again with same string and next index
#
# def check_if_garden_has_question_mark(garden):
#     for item in garden:
#         if item == "?":
#             return True
#     return False
#
# @cache
# def check_all_gardens(current_garden, current_index):
#     if not check_if_garden_has_question_mark(current_garden):
#         result = check_garden(current_garden, current_sequence_list)
#         if result:
#             all_combinations.append(current_garden)
#     else:
#         for item in ["#", "."]:
#             if current_garden[current_index] == "?":
#                 new_garden = replace_at_index(current_garden, item, current_index)
#             else:
#                 new_garden = current_garden
#             check_all_gardens(new_garden, current_index+1)

EXPAND = 5

@cache
def check_garden(garden, sequence_list, streak_of_hashtag):
    # if we have an empty string (base case)
    if garden == "":
        # if sequences are done, and combinations checked is 0, return 1
        # otherwise return 0
        if len(sequence_list) == 0 and streak_of_hashtag == 0:
            return 1
        else:
            return 0

    valid_streak = 0

    # If we see a ? next, replace it with . or #
    # else, just keep the current symbol
    if garden[0] == "?":
        possible_symbols = ( ".", "#" )
    else:
        possible_symbols = garden[0]

    for symbol in possible_symbols:
        if symbol == "#":
            # Get the next garden substring and increase combinations checked by 1 (essentially a pass)
            valid_streak += check_garden(garden[1:], sequence_list, streak_of_hashtag + 1)
        else:
            if streak_of_hashtag != 0:
                # If we have finished the first section of sequence list
                if len(sequence_list) != 0 and sequence_list[0] == streak_of_hashtag:
                    next_garden = garden[1:]
                    next_sequence = sequence_list[1:]
                    # Get the next garden substring, the next sequence, and start the combinations checked at 0
                    valid_streak += check_garden(next_garden, next_sequence, 0)
            else:
                # Keep going to the next section of the string
                next_garden = garden[1:]
                valid_streak += check_garden(next_garden, sequence_list, 0)

    return valid_streak


f = open("Day12_Input", "r")
total = 0
current_sequence_list = ()
lines = []
for line in f:
    info = line.rstrip().split(" ")
    garden = info[0]
    sequence = info[1]

    garden = garden
    current_sequence_list = current_sequence_list
    current_sequence_list = tuple(map(int, sequence.split(",")))
    lines.append((garden, current_sequence_list))

total = 0
for garden, sequence_list in lines:
    total += check_garden("?".join([garden] * EXPAND) + ".", sequence_list * EXPAND, 0)

print(total)