#pragma once
#include <iostream>
#include <vector>
#include <set>
#include <array>
#include <cmath>
#include <cinttypes>
#include "kiss_fft.h"
#include "note.hpp"

#define N 4096
#define Nd2 2048
#define TWO_BYTE_MAX 32768.0f
#define DELTA 40.0f

class Recognizer
{
private:
    kiss_fft_cfg cfg;
    std::array<kiss_fft_cpx, N> in;
    std::array<kiss_fft_cpx, N> out;


public:
    Recognizer()
        : cfg(kiss_fft_alloc(N, 0, nullptr, nullptr))
    {
        if (!cfg) {
            std::cerr << "Failed to initialize Kiss FFT configuration" << std::endl;
        }
    }

    ~Recognizer()
    {
        kiss_fft_free(cfg);
    }

public:
    void store_fft(const std::vector<uint8_t>& input);
    std::vector<int> detect_peaks();
};
