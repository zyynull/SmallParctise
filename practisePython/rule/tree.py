import os

# path = os.getcwd()
path = '/Users/admin/git/cmft/rules/uw/UWRules/规则'


def filenum(currPath):
    '''计算当前目录下文件数'''
    return sum([len(files) for root, dirs, files in os.walk(currPath)])


print('当前路径：%s' % path)
print('文件总数为：%s' % filenum(path))


def showTree(startPath):
    for root, dirs, files in os.walk(startPath):
        filecount = filenum(root)
        level = root.replace(startPath, ' ').count(os.sep)
        deep = '|   ' * level + '|___ '
        print("%s%s" % (deep, os.path.split(root)[1]))
        for file in files:
            deep = '|   ' * (level + 1) + '|___ '
            print("%s%s" % (deep, file))


showTree(path)
