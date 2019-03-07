
## ForkJoinTask

MyTask extends RecursiveTask<Integer> 

MyTask(List<Integer>)

Integer compute()

if (size < 3) computeDirectly

MyTask suba = new MyTask(list.subList(0, size/2))
MyTask subb = new MyTask(list.subList(size/2+1, size-1))
invokeAll(suba, subb)

return suba.join() + subb.join()




