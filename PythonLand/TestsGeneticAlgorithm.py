import random as r
import time



def gene(length, options):
    out = []
    options  = list(options.keys())[:]
    while len(out) < length:
        out += [options.pop(r.randint(0,len(options)-1))]
    return out

def generatePopulation(n, length, options):
    #Generate population of size n
    return [(gene(length, options), 0) for i in range(n)]


def fitness(gene): 
    found = [False for i in options["t0"]]
    fit = 0.0
    #print("fitness\tcount\tt")
    for t in range(len(gene)):
        count = 0.0
        for fault in range(len(options["t0"])):
            if not found[fault] and options[gene[t]][fault] == '1':
                count = count + 1
                found[fault] = True
        #print(str(fit) + "\t" + str(count) + "\t" + str(t))
        fit += (count/(t+1))
    return fit/(len(options["t0"]))
    

def selection(population):
    #sort population by fitness and cull lower half
    population = sorted(population, key=(lambda t : t[1]),reverse=True)
    return population[0:int(len(population)/2)]

def crossover(t1, t2):
    #chops t1[0] and t2[0] at a random place each and returns
    #the combined results. Strings are able to vary in length by
    #this. Could experiment with normalising the values, but this
    #works fine
    ind1 = r.randint(0,len(t1[0]))
    ind2 = ind1
    sol1 = t1[0][:ind1] + t2[0][ind2:]
    sol2 = t2[0][0:ind2]+t1[0][ind1:]
    sol1 = dupes(sol1, t2[0])
    sol2 = dupes(sol2, t1[0])
        
    return [(sol1,0),(sol2 ,0)]

def dupes(s, source):
    #s = list(s)
    for i in range(len(s)):
        if s[i] in s[:i]:
            #print(s)
            for c in source:
                if not c in s:
                    s[i] = c
                    
                    break
    #print(s)
    return s

def mating(population, popsize):
    #crude equivalent of tournament selection. Pick pools of 10, multiply
    #their fitnesses by between 1 and 2 for variance, then pick 1. Repeat
    #and breed the two winners. Repeat until population up to size
    pool = population[:]
    while len(population) < popsize:
        parent1 = [pool[r.randint(0,len(pool)-1)] for i in range(10)]
        parent1 = max(parent1, key = (lambda x : x[1]*(r.random()+1)))
        #pool.remove(parent1)

        parent2 = [pool[r.randint(0,len(pool)-1)] for i in range(10)]
        parent2 = max(parent2, key = (lambda x : x[1]*(r.random()+1)))
        #pool.remove(parent2)
        population.extend(crossover(parent1, parent2))
    return population

def mutations(population):
    #5% chance of mutating each element of the population
    for i in range(len(population)):
        if r.random() <= 0.05:
            population[i] = (mutate(population[i][0]),0)
    return population

def mutate(s):
    i = 0
    #for each character in string
    while i < len(s) - 1:
        rand = r.random()
        #each of the n characters has a 1/n chance to mutate
        if rand <= 0.5/len(s):
            s[i], s[i+1] = s[i+1], s[i]
        
        i += 1
    i = 0
    while i < len(s):
        rand = r.random()
        #each of the n characters has a 1/n chance to mutate
        if rand <= 0.5/len(s):
            s[i] = r.choice(list(options.keys()))
        
        i += 1
    return s

options = dict()
with open("bigfaultmatrix.txt") as source:
    for line in source:
        stuff = line.replace("\n","").split(",")
        options[stuff[0]] = stuff[1:]

def GeneticAlgorithm():
    popsize = 1000
    population = generatePopulation(popsize, 7, options) #initial population

    n = 1 # no of generations
    
    
    maxiter = 1000 # in case you want to limit generations
    while  n < maxiter:
       # print(n)
        population = [(i[0],fitness(i[0])) for i in population] #fitness step
        population = selection(population) #selection step, sorts and culls population
        #if population[0][1] >= maxfit: #termination condition
             #break

        #print(str(population[0][0] + " " + str(population[0][1])))
            
        population = mating(population, popsize)
        population = mutations(population)

        n += 1
    #not needed in normal case, only relevant if maxiter reached to sort population
    population = selection(population)
    print(population[0][0])
    print(population[0][1])
    return (population[0][0],n)

def HillClimber():
    #uses same functions as genetic algorithm
    solution = gene(6, options)
    #predictably terminates, no need for upper bound
    n = 1
    i = 0
    while i < 150000:
        newsol = mutate(solution)
        #keep if its better
        if fitness(newsol) > fitness(solution):
            solution = newsol
            i = 0
        else:
            i += 1
        
        #and round we go again...
        n += 1
        
    return (solution,n)

def trueRandom():
    target = "Hello, World"
    sol = randomString()
    n = 1
    while sol != target:
        sol = randomString()
        n += 1
    return (sol, n)

avg = lambda x : sum(x)/len(x)

if __name__ == '__main__':
    times = []
    outs = []
    for i in range(5):
        start = time.time()
        outs.append(GeneticAlgorithm())
        times.append(time.time()-start)
        print("Done " + str(i) + ": \t" + str(outs[i]))
    #print(times)
