import os

all_file_path_list = []
all_file_sub_dir_list = []


# function to get dir's all file name
def get_all(cwd):
    # list home dir's file or dir name
    get_dir = os.listdir(cwd)
    # judge is dir or file not
    for i in get_dir:
        # get all file or dir path
        sub_dir = os.path.join(cwd, i)
        # judge path
        if os.path.isdir(sub_dir):
            # is dir then call function again
            get_all(sub_dir)

        else:
            # or i.match("", i)
            if i == ".entries" or i == ".rulepackage":
                continue
            else:
                # save the every single file's path
                all_file_path_list.append(i)
                all_file_sub_dir_list.append(sub_dir)


get_all('/Users/admin/git/cmft/rules/uw/UWRules/规则')
print("all_file_path_list")
print(all_file_path_list)
print("---------------------------------------------------------------------")
print("all_file_sub_dir_list")
print(all_file_sub_dir_list)
