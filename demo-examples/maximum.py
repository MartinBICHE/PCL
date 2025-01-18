# Function to find the maximum number in a list
def find_max(lst):
    if len(lst) == 0:
        print "List is empty"
        return None
    max_num = lst[0]
    # Iterate through the list to find the maximum
    for i in range(1, len(lst)):
        if lst[i] > max_num:
            max_num = lst[i]
    # Print the maximum number
    print max_num

# Example usage
find_max([3, 1, 4, 1, 5, 9, 2, 6])
