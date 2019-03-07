
面试题
https://www.cnblogs.com/jchubby/p/5449379.html


## Map Reduce

## Mapper

extends Mapper<LongWritable, Text, Text, IntWritable>
输入key类型，输入value类型，输出key类型，输出value类型

void map(LongWritable, Text, Context)
输入key类型，输入value类型

context.write(Text, IntWritable)
输出key类型，输出value类型


## Reducer

extends Reducer<Text, IntWritable, Text, IntWritable>
输入key类型，输入value类型，输出key类型，输出value类型

void reduce(Text, Iterable<IntWritable>, Context)
输入key类型，输入value类型

context.write(Text, IntWritable)
输出key类型，输出value类型
