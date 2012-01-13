data = load('exp2-100.dat');
max_gap_size = max(data(:,1));

p = zeros(max_gap_size+1, 1);
for gap_size = 0:max_gap_size
    d = data(data(:,1)==gap_size,:);
    d = d(d(:,4) ~= 0, :);
    p(gap_size+1) = mean(d(:,4));
end
plot(0:max_gap_size, p,
    'linewidth', 2,
    'color', 'black');
axis([0, max_gap_size, 0, max(p)])
xlabel('Gap Size');
ylabel('Average number of reconfigurations');
print('plot.pdf',
    '-dpdfwrite',
    '-S200,200',
    '-tight',
    '-FLMRoman10:16');
