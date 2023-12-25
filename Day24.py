import numpy as np
from sympy import Symbol
from sympy import solve_poly_system

def compute_line(p1, p2):
    A = (p1[1] - p2[1])
    B = (p2[0] - p1[0])
    C = (p1[0]*p2[1] - p2[0]*p1[1])
    return A, B, -C

def intersection(L1, L2):
    D  = L1[0] * L2[1] - L1[1] * L2[0]
    Dx = L1[2] * L2[1] - L1[1] * L2[2]
    Dy = L1[0] * L2[2] - L1[2] * L2[0]
    if D != 0:
        x = Dx / D
        y = Dy / D
        return x,y
    else:
        return 0,0


def compute_hailstorms(h1, h2):
    p1 = h1["x"], h1["y"]
    p2 = p1[0] - h1["x_change"], p1[1] - h1["y_change"]

    p3 = h2["x"], h2["y"]
    p4 = p3[0] - h2["x_change"], p3[1] - h2["y_change"]

    line1 = compute_line(p1, p2)
    line2 = compute_line(p3, p4)

    return intersection(line1, line2)

def in_past(x, x_change, x_int):
    if x_change < 0:
        return x_int >= x
    else:
        return x_int <= x


f = open("data", "r")
lines = []

for w in f:
    lines.append(w.rstrip())

hailstorms = []
for line in lines:
    hailstorm = {}
    hail_info = line.split("@")
    x = int(hail_info[0].split(",")[0])
    y = int(hail_info[0].split(",")[1])
    z = int(hail_info[0].split(",")[2])
    x_change = int(hail_info[1].split(",")[0])
    y_change = int(hail_info[1].split(",")[1])
    z_change = int(hail_info[1].split(",")[2])
    hailstorm["x"] = x
    hailstorm["y"] = y
    hailstorm["z"] = z
    hailstorm["x_change"] = x_change
    hailstorm["y_change"] = y_change
    hailstorm["z_change"] = z_change
    hailstorms.append(hailstorm)


TEST_MIN = 200000000000000
TEST_MAX = 400000000000000
count = 0
for i in range(len(hailstorms)):
    j = i+1
    while j < len(hailstorms):
        x_int, y_int = compute_hailstorms(hailstorms[i], hailstorms[j])
        if x_int >= TEST_MIN and x_int <= TEST_MAX and y_int >= TEST_MIN and y_int <= TEST_MAX:
            check1 = (in_past(hailstorms[i]["x"], hailstorms[i]["x_change"], x_int))
            check2 = (in_past(hailstorms[j]["x"], hailstorms[j]["x_change"], x_int))
            if not check1 and not check2:
                count += 1
        j += 1

print(count)

# set up variables for sympy
x = Symbol('x')
y = Symbol('y')
z = Symbol('z')
vx = Symbol('vx')
vy = Symbol('vy')
vz = Symbol('vz')

equations = []
times = []

for index,hail in enumerate(hailstorms[:3]):
    x0 = hail["x"]
    y0 = hail["y"]
    z0 = hail["z"]
    xv = hail["x_change"]
    yv = hail["y_change"]
    zv = hail["z_change"]
    t = Symbol('t'+str(index))
    # (x + vx * t) --> x position of throw
    # (x0 + xv * t) --> x position of hail to hit
    # set them equal (x + vx * t) = (x0 + xv * t)
    eqx = x + vx * t - x0 - xv * t
    eqy = y + vy * t - y0 - yv * t
    eqz = z + vz * t - z0 - zv * t
    equations.append(eqx)
    equations.append(eqy)
    equations.append(eqz)
    times.append(t)

answer = solve_poly_system(equations,*([x,y,z,vx,vy,vz]+times))
print(answer)
x_answer = answer[0][0]
y_answer = answer[0][1]
z_answer = answer[0][2]
print(x_answer + y_answer + z_answer)
