#include "recognition.hpp"
#include <numeric>

void Recognizer::store_fft(const std::vector<uint8_t>& input)
{
    std::array<kiss_fft_cpx, N> fft_average = {};
    const double NUM_CHUNKS = input.size() / N;

    for(size_t i = 0; i < NUM_CHUNKS; i++) {
        std::array<kiss_fft_cpx, N> fft_input = {};

        for(size_t n_sample = 0; n_sample < N; n_sample++) {
            int16_t sample = static_cast<int16_t>(
                                                  (input[(i * N) + n_sample * 2] << 8) |
                                                  (input[(i * N) + n_sample * 2 + 1])
                                                  );
            fft_input[n_sample].r = sample;
            fft_input[n_sample].i = 0.0f;

        }
        std::array<kiss_fft_cpx, N> fft_output;
        kiss_fft(this->cfg, fft_input.data(), fft_output.data());

        for(size_t i = 0; i < N; i++) {
            fft_average[i].r += fft_output[i].r;
            fft_average[i].i += fft_output[i].i;
        }

    }

    for(size_t i = 0; i < N; i++) {
        fft_average[i].r /= NUM_CHUNKS;
        fft_average[i].i /= NUM_CHUNKS;
    }
    this->out = fft_average;
}

std::vector<int> Recognizer::detect_peaks()
{
    std::vector<float> magnitudes(Nd2);

    for(size_t i = 0; i < Nd2; i++) {
        magnitudes[i] = std::sqrt(this->out[i].r * this->out[i].r +
                                  this->out[i].i * this->out[i].i);
    }
    float avg = 2 * std::accumulate(magnitudes.begin(), magnitudes.end(), 0.0f) / magnitudes.size();
    constexpr int window = 50;
    std::vector<int> peak_indices;
    for(size_t i = window; i < Nd2 - window; i++) {
        float left_avg = std::accumulate(magnitudes.begin() + i - window, magnitudes.begin() + i - 1, 0.0f) / window;
        float right_avg = std::accumulate(magnitudes.begin() + i + 1, magnitudes.begin() + i + window, 0.0f) / window;
        //        printf("Left avg %lf\n", left_avg);
        //        printf("Right avg %lf\n", right_avg);
        float dl = magnitudes[i] - left_avg;
        float dr = magnitudes[i] - right_avg;
        if(avg < dr && dl > avg) {
            peak_indices.push_back(i);
        }
    }
    return peak_indices;
}
