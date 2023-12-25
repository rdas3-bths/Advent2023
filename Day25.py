import networkx as nx

f = open("day25_input")
lines = []
graphs = []

for w in f:
    lines.append(w.rstrip())

nodes = {}
for line in lines:
    source = line.split(":")[0]
    destinations = line.split(":")[1].split(" ")[1:]
    nodes[source] = destinations

g = nx.Graph(nodes)
backbones = nx.minimum_edge_cut(g)

g.remove_edges_from(backbones)

# https://networkx.org/documentation/stable/reference/algorithms/generated/networkx.algorithms.components.connected_components.html
answer = [len(c) for c in sorted(nx.connected_components(g), key=len, reverse=True)]
print(answer[0] * answer[1])
