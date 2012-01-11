clear
clc

load results.txt;

i = find(results(:,5) == 100000);
results(i, 4) = 0;
results(i, 5) = 0;

results = sortrows(results, 1:4);

f_results = results(results(:, 5) ~= 0, :);

gaps = unique(results(:, 1));
bots = unique(results(:, 2));
weights = unique(results(:, 3));

gaps_vs_bots = zeros(length(gaps), length(bots));
gaps_vs_weights = zeros(length(gaps), length(weights));
bots_vs_weights = zeros(length(bots), length(weights));

%% Gaps vs. bots
n = 1;
for i = 1: length(gaps)
    for j = 1:length(bots)
        gaps_vs_bots(i, j) = mean(f_results(sum(ismember(f_results(:, [1 2]), [ gaps(i) bots(j)]), 2) == 2, 4));
        n = n + 1;
    end
end

figure;
C = gradient(gaps_vs_bots');
surf(gaps, bots, gaps_vs_bots', C)
xlabel('gap size');
ylabel('# bots');
zlabel('average #reconfigurations');
title('Average number of reconfigurations w.r.t. gap size and number of bots');

%% Gaps vs. weights
n = 1;
for i = 1: length(gaps)
    for j = 1:length(weights)
        gaps_vs_weights(i, j) = mean(f_results(sum(ismember(f_results(:, [1 3]), [ gaps(i) weights(j)]), 2) == 2, 4));
        n = n + 1;
    end
end

figure;
C = gradient(gaps_vs_weights');
surf(gaps, weights, gaps_vs_weights', C)
xlabel('gap size');
ylabel('box weight');
zlabel('average #reconfigurations');
title('Average number of reconfigurations w.r.t. gap size and box weight');

%% Bots vs. weights
n = 1;
for i = 1: length(bots)
    for j = 1:length(weights)
        bots_vs_weights(i, j) = mean(f_results(sum(ismember(f_results(:, [2 3]), [ bots(i) weights(j)]), 2) == 2, 4));
        n = n + 1;
    end
end

figure;
C = gradient(bots_vs_weights');
surf(bots, weights, bots_vs_weights', C)
xlabel('# bots');
ylabel('box weight');
zlabel('average #reconfigurations');
title('Average number of reconfigurations w.r.t. number of bots and box weight');