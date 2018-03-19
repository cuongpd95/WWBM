A = 'Harrison Ford'
B = 'Ridley Scott'
C = 'Philip Dick'
D = 'James Cameron'
PASSAGE = 'Blade Runner is a 1982 American dystopian science fiction action film directed by Ridley Scott and starring Harrison Ford, Rutger Hauer, and Sean Young. The screenplay, written by Hampton Fancher and David Peoples, is loosely based on the novel Do Androids Dream of Electric Sheep? by Philip K. Dick.'


def levenshtein_distance(a, b):
    """ Tinh khoang cach Levenshtein giua 2 xau """
    if len(a) > len(b):
        a, b = b, a
    if len(b) == 0:
        return len(a)
    a_length = len(a) + 1
    b_length = len(b) + 1
    distance_matrix = [[0] * b_length for x in range(a_length)]
    for i in range(a_length):
        distance_matrix[i][0] = i
        for j in range(b_length):
            distance_matrix[0][j] = j
    for i in range(1, a_length):
        for j in range(1, b_length):
            deletion = distance_matrix[i-1][j] + 1
            insertion = distance_matrix[i][j-1] + 1
            substitution = distance_matrix[i-1][j-1]
            if a[i-1] != b[j-1]:
                substitution += 1
            distance_matrix[i][j] = min(insertion, deletion, substitution)
    return distance_matrix[a_length-1][b_length-1]


def title_levenshtein(a, b):
    """ Tieu chi Levenshtein """
    _max = max(len(a), len(b))
    return (_max - levenshtein_distance(a, b)) / _max


def lcs(a, b):
    """ Tieu chi longest common subsequence"""
    lengths = [[0 for j in range(len(b)+1)] for i in range(len(a)+1)]
    for i, x in enumerate(a):
        for j, y in enumerate(b):
            if x == y:
                lengths[i+1][j+1] = lengths[i][j] + 1
            else:
                lengths[i+1][j+1] = max(lengths[i+1][j], lengths[i][j+1])
    result = ""
    x, y = len(a), len(b)
    while x != 0 and y != 0:
        if lengths[x][y] == lengths[x-1][y]:
            x -= 1
        elif lengths[x][y] == lengths[x][y-1]:
            y -= 1
        else:
            assert a[x-1] == b[y-1]
            result = a[x-1] + result
            x -= 1
            y -= 1
    return len(result)


def overlap(a, b):
    """ Tieu chi overlap """
    word_set_a = set(a.split())
    print(word_set_a)
    word_set_b = set(b.split())
    print(word_set_b)
    n = len(word_set_a.intersection(word_set_b))
    print(word_set_a.intersection(word_set_b))
    print(n)
    print(len(word_set_a) + len(word_set_b))
    return n / float(len(word_set_a) + len(word_set_b) - n)


def es(s1, s2):
    """ Tieu chi Exact Substring """
    m = [[0] * (1 + len(s2)) for i in range(1 + len(s1))]
    longest, x_longest = 0, 0
    for x in range(1, 1 + len(s1)):
        for y in range(1, 1 + len(s2)):
            if s1[x - 1] == s2[y - 1]:
                m[x][y] = m[x - 1][y - 1] + 1
                if m[x][y] > longest:
                    longest = m[x][y]
                    x_longest = x
            else:
                m[x][y] = 0
    # return s1[x_longest - longest: x_longest]
    return len(s1[x_longest - longest: x_longest]) / len(s1)


def main():
    print(title_levenshtein('Philip Dick', 'Ridley Scott'))
    print(lcs('ABCDEF', 'ACDEGHF'))
    print(overlap(A, PASSAGE))
    print(es('ABCD sd', 'ABCc'))


if __name__ == '__main__':
    main()
