1.try+catch 
程序的流程是：运行到try块中，如果有异常抛出，则转到catch块去处理。然后执行catch块后面的语句 

2.try+catch+finally 
程序的流程是：运行到try块中，如果有异常抛出，则转到catch块,catch块执行完毕后，执行finally块的代码，再执行finally块后面的代码。

如果没有异常抛出，执行完try块，也要去执行finally块的代码。然后执行finally块后面的语句 

3.try+finally 
程序的流程是：运行到try块中,如果有异常抛出的话，程序转向执行finally块的代码。那末finally块后面的代码还会被执行吗？不会！因为你没有处理异常，所以遇到异常后，执行完finally后，方法就已抛出异常的方式退出了。 
这种方式中要注意的是，由于你没有捕获异常，所以要在方法后面声明抛出异常