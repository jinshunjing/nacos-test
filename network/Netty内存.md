# Netty内存管理

Netty的内存分成Pooled和Unpooled。池化的好处是减少内存的创建和销毁，提高性能。
- PooledByteBufAllocator
- UnpooledByteBufAllocator

又分成heap和direct。Direct的好处是可以减少数据在内核态和用户态之间的拷贝。
- PooledHeapByteBuf
- PooledUnsafeHeapByteBuf
- PooledDirectByteBuf
- PooledUnsafeDirectByteBuf

- UnpooledHeapByteBuf
- UnpooledUnsafeHeapByteBuf
- UnpooledDirectByteBuf
- UnpooledUnsafeDirectByteBuf
- UnpooledUnsafeNoCleanerDirectByteBuf

内存区域：减少多线程的锁竞争；减少内存碎片。
- PoolThreadCache: ThreadLocal
- PoolArena: cpu核数
- PoolChunk: 16M，完全二叉树
- PoolSubpage: 8K, 位图。tiny: 16B * 32; small: 512B, 1024B, 2048B, 4096B

为了实现零拷贝衍生出：
- PooledDuplicatedByteBuf
- PooledSlicedByteBuf：分成多个ByteBuf

- CompositeByteBuf：组合多个ByteBuf
- FixedCompositeByteBuf
- WrappedCompositeByteBuf

- WrappedByteBuf：包裹各种byte数组，ByteBuf

内存泄露：
通过计数器来释放内存：retain增加计数，release减少计数，为0则调用deallocate
- AbstractReferenceCountedByteBuf
- ReferenceCountUtil

PooledByteBuf被GC释放掉，但是没有调用release把DirectByteBuf归还给池。
Netty提供了内存泄露检测机制，默认抽样1%的ByteBuf进行跟踪。
命中了就创建一个虚引用PhantomReference，然后创建一个WrappedByteBuf。在GC发生的时候放入ReferenceQueue。

4个泄露检测级别：禁用Disabled，简单Simple，高级Advanced，偏执Paranoid
- SimpleLeakAwareByteBuf
- AdvancedLeakAwareByteBuf
- ResourceLeakDetector
- ResourceLeakTracker

