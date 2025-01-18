def example_five(): # define function
    for num in [2,3,4,5,6,7,8,9,10]: # loop from 2..10
        is_prime = True # assume prime
        for val in [2,3,4,5,6,7,8,9,10]: # check divisors
            if val < num: # skip val >= num
                if num % val == 0:
                    is_prime = False
        if is_prime == True:
            print "Prime " + str(num)
        else:
            print "Not prime " + str(num)

example_five()
