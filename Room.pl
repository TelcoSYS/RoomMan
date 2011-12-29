use strict;
use warnings;
use List::Util qw(sum min max);
use Data::Dump::Streamer;
$|++;

my $id=0;
my @rooms = map { $_->{id}=$id++; $_ }
           sort { $b->{maxocc} <=> $a->{maxocc} }
(
    {
      name => "Double",
      maxocc => 2,
      maxadults => 2,
      maxchildren => 0,
      maxinfants => 0,
      propertyid => 1,
    },
    {
      name => "Triple",
      maxocc => 3,
      maxadults => 3,
      maxchildren => 3,
      maxinfants => 2,
      propertyid => 1,
    },
    {
      name => "Quad",
      maxocc => 4,
      maxadults => 4,
      maxchildren => 2,
      maxinfants => 2,
      propertyid => 1
    }
);


sub find_best {
    my ( $res, # where the list of possible tuples will go
         $adults,$children,$infants, # how many people
         $rref,  # this is data about the rooms we have already booked
         $depth, # depth for debugging only
         @rcounts, # count of rooms, in the same order as @rooms
       )=@_;

    #print " " x $depth,"$adults,$children,$infants [@rcounts]\n";

    if ( $infants + $adults + $children <= 0 ) {
        # if we get here we either have an error case ( sum < 0)

        if ( $infants + $adults + $children==0 ) {
            # or we have alocated all the people and we can use this
            # combination
            push @$res,$rref;

            @$rref=sort { $a->[3]{name} cmp $b->[3]{name} } @$rref;
            # add in some total info.
            unshift @$rref,0,0;
            foreach my $item (@$rref) {
                next unless ref $item;
                $rref->[0]+=$item->[1]; # wastage
                $rref->[1]++; # rooms
            }
        }
        return;
    }
    # see what the possibilities are
    my @ok;
    foreach my $roomid (0..$#rooms) {
        my $room=$rooms[$roomid];

        next if $rcounts[$roomid]<=0;

        my ($ad,$ch,$inf)=(0,0,0);

        if ($room->{maxinfants} and $infants and $adults) {
            $ad=1;
            $inf=min($room->{maxinfants},$infants);
        }
        if ($room->{maxchildren} and $children and $adults
            and $ad+$inf < $room->{maxocc} )
        {
            $ad||=1;
            $ch = min( $room->{maxocc} - $ad - $inf,
                       $room->{maxchildren},
                       $children);
        }
        if ($room->{maxadults} and $adults-$ad>0
            and $ad+$inf+$ch < $room->{maxocc} )
        {
            $ad+=min($room->{maxocc} - $ad-$inf-$ch,
                     $adults - $ad );
        }

        if ($ad+$ch+$inf) {
            push @ok, [ $ad+$ch+$inf, # total
                        $room->{maxocc} - $ad-$ch-$inf, #wastage
                        $ch + $inf,       # kids
                        $room,            # room hash
                        $ad, $ch, $inf ]  # counts
        }
    }
    return unless @ok;
    # dunno if this sort is really necessary.
    @ok=sort {
                     $a->[1] <=> $b->[1] # minimize wastage!
                     || # get rid of the kids first
                     $b->[2]  <=> $a->[2]
                     || # get rid of as many people as possible
                     $b->[0] <=> $a->[0]
                    } @ok;
    # ok now see what possible followup room bookings can be made
    for my $try (@ok) {
        next unless $try;
        my @rref=(@{$rref||[]},$try);
        my ($total, $wastage, $kids, $room, $ad, $ch, $inf)=@$try;
        local $rcounts[$room->{id}] = $rcounts[$room->{id}] - 1;

        #print "$room->{name} Occupants:$total (Adults:$ad Children:$ch Infants:$inf) [$wastage]\n";
        find_best($res, $adults-$ad, $children-$ch, $infants-$inf,
                  \@rref,$depth+1,
                  @rcounts);
    }
}

sub pair { # utility function
    my ($array1,$array2)=@_;
    return join ", ",map {
        (ref $array1->[$_] ? $array1->[$_]{name} : $array1->[$_]).
        " => $array2->[$_]"
    } (0..min($#$array1,$#$array2));
}

my @people=qw(Adults Children Infants);

for my $cond (
    [[4,1,0],[0,2,2]], #people, rooms
    [[6,0,0],[1,1,4]],
) {
    my @res;
    print "Finding best fit for ".
          pair(\@people,$cond->[0]).
          "\n\t in rooms ".
          pair(\@rooms,$cond->[1])."\n\n";

    find_best(\@res,@{$cond->[0]},undef,0,@{$cond->[1]});
    @res=sort { $a->[0]<=>$b->[0]||$a->[1]<=>$b->[1]}@res;
    my %seen;
    foreach my $res (@res) {
        my ($wastage,$rooms,@trys)=@$res;
        my $room_key=join "-",map { $_->[3]{name} } @trys;
        next if $seen{$room_key}++;
        print "Rooms:$rooms Wastage:$wastage\n";

        foreach my $roomdata (@trys) {
            my ($total,$wastage,$kids, $room, $ad, $ch, $inf)=@$roomdata;
            print "$room->{name} Occupants:$total (Adults:$ad Children:$ch Infants:$inf) [$wastage]\n";
        }
        print "\n";
    }
    print "---\n";
}


