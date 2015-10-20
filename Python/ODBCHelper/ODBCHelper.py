
#This is function to print out some information of server

#Define function
def buildConnectionString(params):
    
    return ";".join(["%s=%s" % (k, v) for k, v in params.items()])

if __name__ == "__main__":
    myParams = {"server" : "localhost", \
                "database" : "master", \
                "uid" : "sa" , \
                "pwd" : "1234$" \
                }

    print buildConnectionString(myParams)
    
def printName(params):
    myParams = "binh"
    print printName(myParams)
    return 