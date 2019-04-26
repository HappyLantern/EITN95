#!/usr/bin/env python3
# By Johan
import matplotlib.pyplot as plt
import os

inputDir = 'output/'
nrows = 3
ncols = 2
size = 3
# - (it would be nicer to get these values from the file names:)
titles = []
titles.append('N = 1000, M = 1000, x = 100, λ = 8, T = 1')
titles.append('N = 1000, M = 1000, x = 10, λ = 80, T = 1')
titles.append('N = 1000, M = 1000, x = 200, λ = 4, T = 1')
titles.append('N = 100, M = 1000, x = 10, λ = 4, T = 4')
titles.append('N = 100, M = 4000, x = 10, λ = 4, T = 1')
titles.append('N = 100, M = 4000, x = 10, λ = 4, T = 4')

fig, axes = plt.subplots(nrows=nrows, ncols=ncols)
fig.tight_layout()
fig.set_figheight(nrows*size)
fig.set_figwidth(ncols*size*1.5)
fig.text(0.5, 0.02, 'measurement', ha='center')
fig.text(0.0, 0.5, 'customers', va='center', rotation='vertical')

i = 0
for filename in sorted(os.listdir(inputDir)):
    if filename.endswith('.txt'):
        x, y = [], []
        for line in open(inputDir + filename, 'r'):
            x.append(len(x))
            y.append(float(line))
        plt.subplot(nrows, ncols, i + 1)
        plt.plot(x, y, linewidth=0.5)
        plt.title(titles[i])
        i += 1
plt.show()
plt.savefig('output/task4.svg')
