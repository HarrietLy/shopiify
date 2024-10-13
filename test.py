
def solution(digitStrings):
    tempSum=0 # need to accumulate this!, exlucding last digit
    checkSum = 0;
    n = len(digitStrings)
    for i in range(n-1, -1,-2):
        curr = digitStrings[i]
        print(i, curr )
        # skip the last
        if i == n-1:
            print("skip last digit check sum!")
            checkSum=int(curr)
            continue
        double = 2 * int(curr)
        tempSum+= sumDigits(double)
        print("double", double)
        print("sumDigits(double)", sumDigits(double))
        print("tempSum", tempSum)
        print("====")
    # get the finalize value then compare to check sum
    finalize = (10 - (tempSum % 10)) % 10
    print(finalize)
    return finalize==checkSum


def sumDigits(doublenum): # max 2, 18 8
    return (doublenum//10) + (doublenum % 10)

print(solution("12345674"))

