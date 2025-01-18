# Function to reverse words in a sentence
def reverse_words(sentence):
    words = []
    word = ""
    # Split the sentence into words manually
    for i in range(0, len(sentence)):
        if sentence[i] != " ":
            word = word + sentence[i]
        else:
            if word != "":
                words = words + [word]
                word = ""
    # Add the last word if exists
    if word != "":
        words = words + [word]
    
    reversed_sentence = ""
    # Iterate through the words in reverse order
    for i in range(len(words)-1, -1, -1):
        reversed_sentence = reversed_sentence + words[i]
        if i != 0:
            reversed_sentence = reversed_sentence + " "
    # Print the reversed sentence
    print reversed_sentence

# Example usage
reverse_words("Hello World from the Team")
