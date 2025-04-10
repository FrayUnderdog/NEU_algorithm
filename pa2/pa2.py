import argparse
import time
import sys

'''
Subproblem: Given a substring of the target string, determine all the possible ways 
    to construct this substring using words from the word bank. For any substring of 
    the target, we need to break it down and see how we can use the available words 
    in the word bank to build it. Once we solve this for smaller substrings, we can 
    combine the solutions to build up to the full target.
    
Decision making: At each step in the recursion, we make the following decision:
    For each word in the word bank, check if the current substring of the target starts 
    with this word. If it does, the decision is to use this word and then solve for the 
    remainder of the target string. If it doesn't, we move to the next word and try again.
    Thus, for a given substring of the target, the decision is to either: Use a word from 
    the word bank and solve for the remainder of the string (i.e., the part of the target 
    after this word). Or Skip the current word and move to the next.
    
Recursion: For any given substring of the target string, we attempt to match each word 
    from the word bank at the beginning of the substring. If a match is found, we 
    recursively try to construct the remainder of the substring after removing the 
    matched word. We collect all the results and add the current word to each solution.
    Base case: When the target string is empty (""), we know that there's exactly one 
    way to construct it which is by using no words at all.

Validation: Use 'cat testcase.txt | while read line; do eval $line; done' to run testcase.txt
    under the same directory, where each line is formatted with a target argument and
     each word in wordbank as a single argument separated by spaces
    'python3 pa2.py -target purple -wordbank purp p ur le purpl'
    
Error Handling: argparse makes sure to interpret _target input as a string and _wordbank inputs as 
    a list of strings. If there is no _target input or _wordbank inputs, the program will put out 
    an error message to indicate that inputs are missing and then exits.
'''

def all_construct(target, wordbank):
    # Memoization dictionary to store results for subproblems
    dp = {}

    # Helper function that recursively checks all possible ways to construct a substring of the target
    def helper(sub_target):
        # If we've already computed this sub_target, return its result
        if sub_target in dp:
            return dp[sub_target]

        # Base case: if the sub_target is empty, there's one way to construct it: use no words
        if sub_target == "":
            return [[]]

        # Create a result list that will filled up and returned
        result = []

        for word in wordbank:
            # If the sub_target starts with the word, we proceed to the next part of the target
            if sub_target.startswith(word):
                # Recursively find all the ways to construct the remainder of the target
                suffix = sub_target[len(word):]
                suffix_ways = helper(suffix)

                # For each way to construct the suffix, add the current word
                for way in suffix_ways:
                    result.append([word] + way)

        # Memoize and return the result for the current sub_target
        dp[sub_target] = result
        # print(f"{sub_target}: {result}")
        return result

    return helper(target)

def handle_missing_argument():
    print("Please provide input: both -target and -wordbank arguments are required.")
    sys.exit(1)

def main():
    # Read input using argparse from command line arguments
    parser = argparse.ArgumentParser()
    parser.add_argument('-target', type=str, required=True)
    parser.add_argument('-wordbank', type=str, required=True, nargs='+')
    try:
        args = parser.parse_args()

        # Start timer
        start_time = time.time()

        # Call the dynamic programming function
        result = all_construct(args.target, args.wordbank)

        # End timer
        end_time = time.time()
        runtime = end_time - start_time

        # Print the number of ways and the combinations
        print(f"Number of ways: {len(result)}")
        print("[")
        for combination in result:
            print(f"  {combination}")
        print("]")

        # Print the runtime in seconds
        print(f"Runtime: {runtime:.6f} seconds")

    except SystemExit:
        # If -target or -wordbank is missing, handle the error
        handle_missing_argument()

if __name__ == "__main__":
    main()
