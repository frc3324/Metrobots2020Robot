import matplotlib.pyplot as plt
import sys

if (len(sys.argv) < 3):
    sys.stderr.write("Please provide a filename and a x-label")
    sys.exit()

file = open(sys.argv[1], "r")
values = file.readline()
times = file.readline()
values = [float(x) for x in values.split(',')]
times = [float(x) for x in times.split(',')]
plt.ylabel(sys.argv[2])
plt.xlabel("Time")
plt.title(str(sys.argv[2]) + " vs. time")
plt.plot(times, values)
plt.show()

