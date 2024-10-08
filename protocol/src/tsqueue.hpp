#pragma once

#include <atomic>

template <typename DataType, size_t TSQSIZE>
class tsqueue
{
private:
    std::atomic<size_t> tail = 0;
    std::atomic<size_t> head = 0;
    DataType data[TSQSIZE];
    size_t increment(size_t idx) const
    {
        return (idx + 1) % TSQSIZE;
    }
public:
    bool push(DataType& item)
    {
        const size_t curr_tail = tail.load(std::memory_order_relaxed);
        const size_t next_tail = increment(curr_tail);
        if(next_tail != head.load(std::memory_order_acquire)) {
            data[curr_tail] = item;
            tail.store(next_tail, std::memory_order_release);
            return true;
        }
        return false;
    }

    bool pop(DataType& item)
    {
        const size_t curr_head = head.load(std::memory_order_relaxed);
        if(curr_head == tail.load(std::memory_order_acquire)){
            return false;
        }
        const size_t next_head = increment(curr_head);
        item = data[curr_head];
        head.store(next_head, std::memory_order_release);
        return true;
    }
};
