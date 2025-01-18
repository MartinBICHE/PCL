# Function to find common elements in two lists
def common_elements(lst1, lst2):
    common = []
    # Iterate through first list
    for i in range(0, len(lst1)):
        # Iterate through second list
        for j in range(0, len(lst2)):
            if lst1[i] == lst2[j]:
                # Avoid duplicates in common list
                already_present = False
                for k in range(0, len(common)):
                    if common[k] == lst1[i]:
                        already_present = True
                        break
                if not already_present:
                    common = common + [lst1[i]]
    # Print the common elements
    print common

# Example usage
common_elements([1, 2, 3, 4], [3, 4, 5, 6])
