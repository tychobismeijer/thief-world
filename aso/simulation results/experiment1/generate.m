data = load('exp1-100.dat');
max_gap_size = max(data(:,1));
max_n_bots = max(data(:,2));
for gap_size = 0:max_gap_size
    p = zeros(max_n_bots, 1);
    for n_bots = 1:max_n_bots
        d = data(data(:,1)==gap_size,:);
        d = d(d(:,2)==n_bots,:);
        p(n_bots) = sum(d(:,4) == 0) / length(d);
    end
    plot(1:max_n_bots, 100-p*100);
    xlabel('Number of bots');
    ylabel('% Succesfull runs');
    print(['plot' num2str(gap_size) '.eps'], '-deps');
end
